package com.example.smartcitycloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.smartcitycloud.dao")
public class SmartCityCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCityCloudApplication.class, args);
    }

}
