package com.pay.rabbitmq;

import com.pay.entity.MqOrder;
import com.pay.entity.UserOrder;
import com.pay.mapper.MqOrderMapper;
import com.pay.mapper.UserOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Component
@Slf4j
public class RabbitMQComsumer extends RabbitMQMessageDispose{

    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private MqOrderMapper mqOrderMapper;

    @RabbitListener(queues = "common.test.queue",containerFactory = "singleContainerFactory")
    public void testComsum(@Payload byte[] body, @Header("context-type") Object contextType) throws IOException {
        System.out.println("context-type:"+contextType);
        log.info("message : {}",parseMessage(body,String.class));
    }

    @RabbitListener(queues = DelayQueueConfig.ORDER_LETTER_PAY_DEAD_QUEUE,containerFactory = "singleContainerFactory")
    public void deadLetterUserOrderExpire(@Payload byte[] body) throws IOException {
        UserOrder userOrder = parseMessage(body,UserOrder.class);

        log.info("失效订单信息：" + userOrder);

        //检测用户订单是否人仍旧处于确认状态
        UserOrder dbUserOrder = userOrderMapper.selectByIdAndStatus(userOrder.getStatus(),userOrder.getId());

        log.info("订单检测信息：" + dbUserOrder);

        if(dbUserOrder!=null){
            log.info("订单失效： 订单编号-{}，用户id-{}",userOrder.getOrderNo(),userOrder.getUserId());
            userOrderMapper.updateOrderActiveById(userOrder.getId(),1);

            MqOrder mqOrder = new MqOrder();
            mqOrder.setOrderId(userOrder.getId());
            mqOrder.setBusinessTime(new Date());
            mqOrder.setMemo("订单失效：订单编号-"+userOrder.getOrderNo()+",用户id-"+userOrder.getUserId());
            mqOrderMapper.insertSelective(mqOrder);
        }
    }

}
