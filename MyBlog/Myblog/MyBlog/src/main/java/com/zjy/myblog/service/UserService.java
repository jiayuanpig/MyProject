package com.zjy.myblog.service;

import com.zjy.myblog.po.User;

public interface UserService {

    /*检查用户登录*/
    User checkUser(String username, String password);


}
