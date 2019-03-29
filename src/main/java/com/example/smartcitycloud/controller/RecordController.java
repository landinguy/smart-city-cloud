package com.example.smartcitycloud.controller;

import com.example.smartcitycloud.entity.Record;
import com.example.smartcitycloud.service.RecordService;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.RecordReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
