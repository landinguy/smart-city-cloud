package com.example.smartcitycloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.smartcitycloud.entity.Record;
import com.example.smartcitycloud.service.RecordService;
import com.example.smartcitycloud.util.Helper;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.RecordReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.xerces.dom.PSVIAttrNSImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@RestController
public class RecordController {

    @Resource
    private RecordService recordService;

    @PostMapping("addRecord")
    public Result select(@RequestBody Record record) {
        try {
            log.info("add device data,req#{}", record);
            return recordService.add(record);
        } catch (Exception e) {
            log.error("add device data error#{}", e);
            return Result.builder().code(-1).msg("添加设备采集数据失败").build();
        }
    }

    @PostMapping("getRecord")
    public Result select(@RequestBody RecordReq req) {
        try {
            log.info("get device data,req#{}", req);
            return recordService.select(req);
        } catch (Exception e) {
            log.error("get device data error#{}", e);
            return Result.builder().code(-1).msg("查询设备采集数据失败").build();
        }
    }

    @PostMapping("upload")
    public Result upload(MultipartFile file) {
        try {
            log.info("upload file,filename#{}", file.getOriginalFilename());
            String filename = UUID.randomUUID().toString().replaceAll("-", "") + ".txt";
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("D:\\scc\\upload\\" + filename));
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.schedule(() -> {
                try (Stream<String> lines = Files.lines(Paths.get("D:\\scc\\upload\\" + filename), StandardCharsets.UTF_8)) {
                    lines.forEach(item ->
                            Helper.matchedVars(item).forEach(data -> {
                                try {
                                    String[] split = data.split(",");
                                    Record record = new Record();
                                    record.setDeviceId(Integer.valueOf(split[0]));
                                    record.setContent(split[3]);
                                    record.setTime(String.valueOf(Helper.str2Timestamp(split[4])));
                                    recordService.add(record);
                                } catch (Exception e) {
                                    log.info("Incorrect data#{}", data);
                                }
                            })
                    );
                } catch (Exception e) {
                    log.info("do file#{} error#{}", file.getOriginalFilename(), e);
                }
            }, 0, TimeUnit.SECONDS);
            return Result.builder().code(0).msg("文件上传成功").build();
        } catch (Exception e) {
            log.error("upload error#{}", e);
            return Result.builder().code(-1).msg("文件上传失败").build();
        }
    }

}
