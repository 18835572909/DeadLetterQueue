package com.pay.service.impl;

import com.pay.common.APIResponse;
import com.pay.entity.UserOrder;
import com.pay.mapper.UserOrderMapper;
import com.pay.rabbitmq.RabbitMQProducer;
import com.pay.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private RabbitMQProducer mqProducer;
    @Autowired
    private UserOrderMapper userOrderMapper;

    @Override
    public APIResponse userOrderPush(UserOrder userOrder) {
        //1. 检测用户信息
        //2. 检测订单信息
        //3. 添加用户订单
        userOrder.setCreateTime(new Date());
        userOrder.setIsActive(0);
        userOrder.setStatus(0);
        long orderId = userOrderMapper.insertSelective(userOrder);
        //4. 发送信息至延迟队列
        mqProducer.userOrderPush(userOrder);
        return APIResponse.newInstance(0,"下单成功");
    }
}
