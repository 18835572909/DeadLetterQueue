package com.pay.service.impl;

import com.pay.entity.User;
import com.pay.mapper.UserMapper;
import com.pay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectById(Long userId){
        return userMapper.selectById(userId);
    }

}
