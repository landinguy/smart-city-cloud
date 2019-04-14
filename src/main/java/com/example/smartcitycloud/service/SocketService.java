package com.example.smartcitycloud.service;

import com.example.smartcitycloud.dao.DeviceMapper;
import com.example.smartcitycloud.entity.Device;
import com.example.smartcitycloud.entity.Record;
import com.example.smartcitycloud.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*** socket服务端 ***/
@Slf4j
@Component
public class SocketService implements CommandLineRunner {

    @Resource
    private ThreadPoolTaskExecutor myExecutor;
    @Resource
    private RecordService recordService;
    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            log.info("服务端已启动监听......");
            while (true) {
                Socket socket = serverSocket.accept();
                myExecutor.execute(() -> {
                    try (BufferedInputStream bis = new BufferedInputStream(socket.getInputStream())) {
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = bis.read(bytes)) != -1) {
                            String data = new String(bytes, 0, len);
                            Helper.matchedVars(data).forEach(item -> {
                                try {
                                    String[] split = item.split(",");
                                    String id = split[0];
                                    String key = split[5];
                                    Device device = deviceMapper.selectByPrimaryKey(Integer.valueOf(id));
                                    if (device != null) {
                                        if (device.getDeviceKey().equals(key)) {
                                            Record record = new Record();
                                            record.setDeviceId(Integer.valueOf(split[0]));
                                            record.setContent(split[3]);
                                            record.setTime(String.valueOf(Helper.str2Timestamp(split[4])));
                                            recordService.add(record);
                                        } else {
                                            log.info("key is unmatched,deviceId#{},key#{}", id, key);
                                        }
                                    } else {
                                        log.info("device doesn't exist,deviceId#{}", id);
                                    }
                                } catch (Exception e) {
                                    log.info("Incorrect data#{}", data);
                                }
                            });
                        }
                    } catch (Exception e) {
                        log.error("receive data from client error#{}", e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("server start error#{}", e);
        }
    }
}
