package com.example.smartcitycloud.controller;

import com.example.smartcitycloud.dao.CredentialsMapper;
import com.example.smartcitycloud.entity.Credentials;
import com.example.smartcitycloud.entity.CredentialsExample;
import com.example.smartcitycloud.entity.Device;
import com.example.smartcitycloud.service.DeviceService;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.DeviceReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class DeviceController {

    @Resource
    private DeviceService deviceService;
    @Resource
    private CredentialsMapper credentialsMapper;

    /*** 添加设备 ***/
    @PostMapping("addDevice")
    public Result addDevice(@RequestBody Device device) {
        try {
            log.info("add or update device,params#{}", device);
            return deviceService.add(device);
        } catch (Exception e) {
            log.error("add or update device error#{}", e);
            return Result.builder().code(-1).msg("添加或修改设备失败").build();
        }
    }

    /*** 删除设备 ***/
    @RequestMapping("deleteDevice")
    public Result addDevice(Integer id) {
        try {
            log.info("delete device,id#{}", id);
            return deviceService.delete(id);
        } catch (Exception e) {
            log.error("delete device error#{}", e);
            return Result.builder().code(-1).msg("删除设备失败").build();
        }
    }

    /*** 查询设备 ***/
    @PostMapping("getDevice")
    public Result getDevice(@RequestBody DeviceReq req) {
        try {
            log.info("get device,req#{}", req);
            return deviceService.select(req);
        } catch (Exception e) {
            log.error("get device error#{}", e);
            return Result.builder().code(-1).msg("查询设备失败").build();
        }
    }

    /*** 校验设备key ***/
    @GetMapping("checkCredentials")
    public Result checkCredentials(String deviceKey) {
        try {
            log.info("checkCredentials,deviceKey#{}", deviceKey);
            CredentialsExample example = new CredentialsExample();
            example.createCriteria().andCidEqualTo(deviceKey).andStatusEqualTo(0);
            List<Credentials> list = credentialsMapper.selectByExample(example);
            if (list.size() > 0) {
                return Result.builder().code(0).msg("success").data(true).build();
            }
            return Result.builder().code(-1).msg("fail").data(false).build();
        } catch (Exception e) {
            log.error("checkCredentials error#{}", e);
            return Result.builder().code(-1).msg("校验设备key失败").build();
        }
    }

}
