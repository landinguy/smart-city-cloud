package com.example.smartcitycloud.service;

import com.alibaba.fastjson.JSONObject;
import com.example.smartcitycloud.dao.UserMapper;
import com.example.smartcitycloud.entity.User;
import com.example.smartcitycloud.entity.UserExample;
import com.example.smartcitycloud.util.Consts;
import com.example.smartcitycloud.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

/**
 * Created by xxf on 2019/3/28 0028.
 */
@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private HttpServletRequest request;

    public Result checkAndLogin(String username, String password) throws Exception {
        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andPasswordEqualTo(Base64Utils.encodeToString(password.getBytes("utf-8")));
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            User user = users.get(0);
            JSONObject data = new JSONObject();
            data.fluentPut("username", user.getUsername())
                    .fluentPut("nickname", user.getNickname()).fluentPut("uid", user.getId());

            request.getSession().setAttribute(Consts.SEESION_UNAME, user.getUsername());
            request.getSession().setMaxInactiveInterval(30 * 60);

            return Result.builder().code(0).msg("success").data(data).build();
        }
        return Result.builder().code(-1).msg("用户名或者密码错误").build();
    }
}
