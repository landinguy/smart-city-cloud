package com.example.smartcitycloud.view;

import lombok.Data;

@Data
public class DeviceReq {
    private Integer pageNo;
    private Integer pageSize;
    private String name;
    private String type;
    private Integer uid;
}
