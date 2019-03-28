package com.example.smartcitycloud.controller;

import com.example.smartcitycloud.service.UserService;
import com.example.smartcitycloud.util.Consts;
import com.example.smartcitycloud.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @ResponseBody
    @GetMapping("login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            log.info("username#{},password#{}", username, password);
            return userService.checkAndLogin(username, password);
        } catch (Exception e) {
            return Result.builder().code(-1).msg("系统异常").build();
        }
    }

    @ResponseBody
    @GetMapping("logout")
    public Result logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(Consts.SEESION_UNAME);
            session.removeAttribute(Consts.SEESION_UID);
        }
        return Result.builder().build();
    }



}
