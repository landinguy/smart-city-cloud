package com.example.smartcitycloud.controller;

import com.example.smartcitycloud.entity.User;
import com.example.smartcitycloud.service.UserService;
import com.example.smartcitycloud.util.Consts;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /*** 登录 ***/
    @GetMapping("login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            log.info("username#{},password#{}", username, password);
            return userService.checkAndLogin(username, password);
        } catch (Exception e) {
            return Result.builder().code(-1).msg("系统异常").build();
        }
    }

    /*** 登出 ***/
    @GetMapping("logout")
    public Result logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(Consts.SEESION_UNAME);
            session.removeAttribute(Consts.SEESION_UID);
        }
        return Result.builder().build();
    }

    /*** 添加账号 ***/
    @PostMapping("addAccount")
    public Result add(@RequestBody User user) {
        try {
            log.info("add or update user,params#{}", user);
            return userService.add(user);
        } catch (Exception e) {
            log.error("add or update user error#{}", e);
            return Result.builder().code(-1).msg("添加或修改用户失败").build();
        }
    }

    /*** 更新密码 ***/
    @GetMapping("updatePwd")
    public Result updatePwd(String oldPassword, String newPassword) {
        try {
            log.info("update password,oldPassword#{},newPassword#{}", oldPassword, newPassword);
            return userService.updatePassword(oldPassword, newPassword);
        } catch (Exception e) {
            log.error("update password error#{}", e);
            return Result.builder().code(-1).msg("修改密码失败").build();
        }
    }

    /*** 查询账号 ***/
    @PostMapping("getAccount")
    public Result getAccount(@RequestBody UserReq req) {
        try {
            log.info("getAccount,params#{}", req);
            return userService.select(req);
        } catch (Exception e) {
            log.error("getAccount error#{}", e);
            return Result.builder().code(-1).msg("查询用户失败").build();
        }
    }


}
