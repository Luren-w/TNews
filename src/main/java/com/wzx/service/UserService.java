package com.wzx.service;

import com.wzx.po.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User CheckUser(String username, String password);
}
