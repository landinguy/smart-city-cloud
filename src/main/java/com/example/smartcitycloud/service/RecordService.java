package com.example.smartcitycloud.service;

import com.alibaba.fastjson.JSONObject;
import com.example.smartcitycloud.dao.RecordMapper;
import com.example.smartcitycloud.entity.Record;
import com.example.smartcitycloud.entity.RecordExample;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.RecordReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class RecordService {
    @Resource
    private RecordMapper recordMapper;

    @Resource
    private HttpServletRequest request;

    public Result add(Record record) {
        if (StringUtils.isEmpty(record.getTime())) record.setTime(String.valueOf(System.currentTimeMillis()));
        int i = recordMapper.insertSelective(record);
        if (i > 0) {
            log.info("add device data successfully");
            return Result.builder().code(0).msg("success").build();
        }
        return Result.builder().code(-1).msg("fail").build();
    }

    public Result select(RecordReq req) {
        RecordExample example = new RecordExample();
        example.createCriteria().andDeviceIdEqualTo(req.getId());
        long total = recordMapper.countByExample(example);
        example.setPageNo((req.getPageNo() - 1) * req.getPageSize());
        example.setPageSize(req.getPageSize());
        List<Record> list = recordMapper.selectByExample(example);
        JSONObject data = new JSONObject();
        data.fluentPut("list", list).fluentPut("total", total);
        return Result.builder().code(0).msg("success").data(data).build();
    }
}
