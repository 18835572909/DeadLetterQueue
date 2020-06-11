package com.pay.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Configuration
public class DelayQueueConfig {

    public static final String ORDER_PAY_DELAY_EXCHANGE = "order.pay.delay.exchenge";
    public static final String ORDER_PAY_DELAY_QUEUE = "order.pay.delay.queue";
    public static final String ORDER_PAY_DELAY_ROUTING_KEY = "order.pay.delay.routingkey";

    public static final String ORDER_LETTER_PAY_DEAD_EXCHANGE = "order.pay.dead.letter.exchenge";
    public static final String ORDER_LETTER_PAY_DEAD_QUEUE = "order.pay.dead.letter.queue";
    public static final String ORDER_LETTER_PAY_DEAD_ROUTING_KEY = "order.pay.dead.letter.routingkey";

    @Bean("delayOrderQueue")
    public Queue queue1(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",ORDER_LETTER_PAY_DEAD_EXCHANGE);
        args.put("x-dead-letter-routing-key",ORDER_LETTER_PAY_DEAD_ROUTING_KEY);
        args.put("x-message-ttl",30000);
        return QueueBuilder.durable(ORDER_PAY_DELAY_QUEUE).withArguments(args).build();
    }

    @Bean("delayOrderExchange")
    public DirectExchange exchange1(){
        return new DirectExchange(ORDER_PAY_DELAY_EXCHANGE);
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(exchange1()).with(ORDER_PAY_DELAY_ROUTING_KEY);
    }

    @Bean("deadLetterOrderQueue")
    public Queue queue2(){
        return QueueBuilder.durable(ORDER_LETTER_PAY_DEAD_QUEUE).build();
    }

    @Bean("deadLetterOrderExchange")
    public DirectExchange exchange2(){
        return new DirectExchange(ORDER_LETTER_PAY_DEAD_EXCHANGE);
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(exchange2()).with(ORDER_LETTER_PAY_DEAD_ROUTING_KEY);
    }

}
