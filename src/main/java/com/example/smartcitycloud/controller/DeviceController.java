package com.example.smartcitycloud.controller;

import com.example.smartcitycloud.entity.Device;
import com.example.smartcitycloud.service.DeviceService;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.DeviceReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class DeviceController {

    @Resource
    private DeviceService deviceService;

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

}
