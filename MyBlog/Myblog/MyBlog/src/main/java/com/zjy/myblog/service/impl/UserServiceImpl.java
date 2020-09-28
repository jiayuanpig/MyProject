package com.zjy.myblog.service.impl;

import com.zjy.myblog.dao.UserRepository;
import com.zjy.myblog.po.User;
import com.zjy.myblog.service.UserService;
import com.zjy.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        //密码加密
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
