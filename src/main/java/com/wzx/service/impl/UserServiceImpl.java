package com.wzx.service.impl;

import com.wzx.dao.UserDao;
import com.wzx.po.User;
import com.wzx.service.UserService;
import com.wzx.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User CheckUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username, MD5Util.code(password));
    }
}
