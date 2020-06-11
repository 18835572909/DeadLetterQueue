package com.pay.controller;

import com.pay.entity.User;
import com.pay.rabbitmq.RabbitMQProducer;
import com.pay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitMQProducer producer;

    @GetMapping("/hello")
    public String test(){
        return "hello world";
    }

    @GetMapping("user/{userId}")
    public User userById(@PathVariable Long userId){
        return userService.selectById(userId);
    }

    @GetMapping("/mq/test")
    public String testRabbitMQ(){
        producer.test();
        return "hello";
    }

}
