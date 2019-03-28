package com.example.smartcitycloud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private Date createTs;

    private Date updateTs;
}