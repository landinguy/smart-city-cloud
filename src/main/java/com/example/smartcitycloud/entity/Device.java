package com.example.smartcitycloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Device {
    private Integer id;

    private String name;

    private Integer type;

    private String description;

    private Integer uid;

    private String username;

    private Date createTs;

    private Date updateTs;

    private String deviceKey;
}