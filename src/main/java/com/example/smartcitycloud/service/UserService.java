package com.example.smartcitycloud.service;

import com.alibaba.fastjson.JSONObject;
import com.example.smartcitycloud.dao.UserMapper;
import com.example.smartcitycloud.entity.User;
import com.example.smartcitycloud.entity.UserExample;
import com.example.smartcitycloud.util.Consts;
import com.example.smartcitycloud.util.Result;
import com.example.smartcitycloud.view.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private HttpSession session;

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
                    .fluentPut("nickname", user.getNickname())
                    .fluentPut("uid", user.getId())
                    .fluentPut("role", user.getRole());

            session.setAttribute(Consts.SEESION_UNAME, user.getUsername());
            session.setAttribute(Consts.SEESION_UID, user.getId());
            session.setMaxInactiveInterval(30 * 60);

            return Result.builder().code(0).msg("success").data(data).build();
        }
        return Result.builder().code(-1).msg("用户名或者密码错误").build();
    }

    public Result add(User user) throws Exception {
        Integer id = user.getId();
        Date now = new Date();
        user.setUpdateTs(now);
        int i;
        if (id != null) {
            user.setPassword(null);
            i = userMapper.updateByPrimaryKeySelective(user);
        } else {
            user.setCreateTs(now);
            user.setPassword(Base64Utils.encodeToString(user.getPassword().getBytes("utf-8")));
            i = userMapper.insertSelective(user);
        }
        if (i > 0) {
            log.info("save or update user successfully,user#{}", user);
            return Result.builder().code(0).msg("success").build();
        }
        return Result.builder().code(-1).msg("fail").build();
    }

    public Result select(UserReq req) {
        UserExample example = new UserExample();
        long total = userMapper.countByExample(example);
        if (req.getPageNo() != null && req.getPageSize() != null) {
            example.setPageNo((req.getPageNo() - 1) * req.getPageSize());
            example.setPageSize(req.getPageSize());
        }
//        UserExample.Criteria criteria = example.createCriteria();
//        if (!StringUtils.isEmpty(req.getUsername())) {
//            criteria.andUsernameLike(req.getUsername() + "%");
//        }
        JSONObject data = new JSONObject();
        List<User> users = userMapper.selectByExample(example);
        List<JSONObject> list = users.stream().map(item -> {
            JSONObject jo = new JSONObject();
            jo.fluentPut("id", item.getId())
                    .fluentPut("username", item.getUsername())
                    .fluentPut("nickname", item.getNickname())
                    .fluentPut("role", item.getRole());
            return jo;
        }).collect(Collectors.toList());
        data.fluentPut("list", list).fluentPut("total", total);
        return Result.builder().msg("success").data(data).build();
    }

    public Result updatePassword(String oldPassword, String newPassword) throws Exception {
        Integer id = (Integer) session.getAttribute(Consts.SEESION_UID);
        if (id != null) {
            User user = userMapper.selectByPrimaryKey(id);
            if (!user.getPassword().equals(Base64Utils.encodeToString(oldPassword.getBytes("utf-8")))) {
                return Result.builder().code(-1).msg("旧密码错误").build();
            }
            user.setPassword(Base64Utils.encodeToString(newPassword.getBytes("utf-8")));
            int i = userMapper.updateByPrimaryKeySelective(user);
            if (i > 0) {
                return Result.builder().code(0).msg("success").build();
            }
        }
        return Result.builder().code(-1).msg("fail").build();
    }
}
