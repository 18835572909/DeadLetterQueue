package com.pay.service;

import com.pay.entity.User;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
public interface UserService {

    public User selectById(Long userId);

}
