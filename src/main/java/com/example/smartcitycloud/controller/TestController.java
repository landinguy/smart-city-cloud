package com.example.smartcitycloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @ResponseBody
    @RequestMapping("hello")
    public String hello() {
        return "hello,landing guy!";
    }

}
