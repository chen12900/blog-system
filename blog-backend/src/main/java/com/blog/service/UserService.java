package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.User;

public interface UserService extends IService<User> {
    User register(String username, String password, String email);
    String login(String username, String password);
    User getById(Long id);
}
