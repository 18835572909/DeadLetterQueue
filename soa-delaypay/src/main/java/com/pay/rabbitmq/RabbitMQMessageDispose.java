package com.pay.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
public class RabbitMQMessageDispose {

    protected void sendMessage(RabbitTemplate rabbitTemplate,String routingKey,String exchange,Object body){
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        Message message = null;
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            message = MessageBuilder.withBody(new ObjectMapper().writeValueAsBytes(body))
                                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                                    .build();

            message.getMessageProperties()
                    .setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,MessageProperties.CONTENT_TYPE_JSON);
        }catch (Exception e){

        }
        rabbitTemplate.correlationConvertAndSend(message,correlationData);
    }

    protected <T> T parseMessage(byte[] body,Class<T> cla) throws IOException {
        return new ObjectMapper().readValue(body,cla);
    }

}
