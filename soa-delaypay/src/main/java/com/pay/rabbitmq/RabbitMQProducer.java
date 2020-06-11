package com.pay.rabbitmq;

import com.pay.entity.UserOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Component
public class RabbitMQProducer extends RabbitMQMessageDispose{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void test(){
        sendMessage(rabbitTemplate,"common.test","common.test.direct.exchenge","hello world");
    }

    public void userOrderPush(UserOrder userOrder){
        sendMessage(rabbitTemplate,DelayQueueConfig.ORDER_PAY_DELAY_ROUTING_KEY,DelayQueueConfig.ORDER_PAY_DELAY_EXCHANGE,userOrder);
    }
}
