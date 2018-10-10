package com.jjs.csdn.service;

import com.jjs.csdn.entity.User;
import com.jjs.csdn.mapper.UserMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public User getUserByUserName(String userName){

        return userMapper.getUserByUserName(userName);

    }

    public User login(String username, String pwd) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
            throw new Exception("有必填参数");
        }

        final User user = userMapper.getUserByUserName(username);
        if (null == user) {
            throw new Exception("用户名不存在或者密码错误");
        }

        if (!pwd.equals(user.getPassword())) {
            throw new Exception("用户名不存在或者密码错误");
        }

        return user;
    }
}
