package com.example.smartcitycloud.service;

import com.alibaba.fastjson.JSONObject;
import com.example.smartcitycloud.dao.CredentialsMapper;
import com.example.smartcitycloud.dao.DeviceMapper;
import com.example.smartcitycloud.dao.RecordMapper;
import com.example.smartcitycloud.dao.UserMapper;
import com.example.smartcitycloud.entity.Device;
import com.example.smartcitycloud.entity.DeviceExample;
import com.example.smartcitycloud.util.Helper;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.DeviceReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DeviceService {
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private CredentialsMapper credentialsMapper;

    @Resource
    private HttpServletRequest request;


    @Transactional
    public Result add(Device device) {
        Integer id = device.getId();
        device.setUpdateTs(new Date());
        int i;
        if (id != null) {
            i = deviceMapper.updateByPrimaryKeySelective(device);
        } else {
            device.setDeviceKey(Helper.getUUID());
            i = deviceMapper.insertSelective(device);
        }
        if (i > 0) {
            log.info("add or update device successfully");
            return Result.builder().code(0).msg("success").build();
        }
        return Result.builder().code(-1).msg("fail").build();
    }

    public Result delete(Integer id) {
        int i = deviceMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            log.info("delete device successfully,deviceId#{}", id);
            return Result.builder().code(0).msg("success").build();
        }
        return Result.builder().code(-1).msg("fail").build();
    }

    public Result select(DeviceReq req) {
        DeviceExample example = new DeviceExample();
        if (req.getUid() != null) {
            example.createCriteria().andUidEqualTo(req.getUid());
        }
        long total = deviceMapper.countByExample(example);
        if (req.getPageNo() != null && req.getPageSize() != null) {
            example.setPageNo((req.getPageNo() - 1) * req.getPageSize());
            example.setPageSize(req.getPageSize());
        }
        JSONObject data = new JSONObject();
        List<Device> list = deviceMapper.selectByExample(example);
        data.fluentPut("list", list).fluentPut("total", total);
        return Result.builder().msg("success").data(data).build();
    }
}
