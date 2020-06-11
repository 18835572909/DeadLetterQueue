package com.pay.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Configuration
public class CommonQueueConfig {

    @Bean("commonQueue1")
    public Queue queue(){
        return QueueBuilder.durable("common.test.queue").build();
    }

    @Bean("commmonDirectExchange")
    public DirectExchange directExchange(){
        return new DirectExchange("common.test.direct.exchenge");
    }

    @Bean("commonBinding")
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with("common.test");
    }

}
