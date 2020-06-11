package com.pay.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author: rhb
 * @date: ${date} ${time}
 * @description:
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private Environment env;
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer rabbitListenerContainerFactoryConfigurer;

    @Bean(name="singleContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleContainerFactory(){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setConcurrentConsumers(1);
        containerFactory.setMaxConcurrentConsumers(1);
        containerFactory.setPrefetchCount(1);
        containerFactory.setTxSize(1);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());

        rabbitListenerContainerFactoryConfigurer.configure(containerFactory,connectionFactory);
        return containerFactory;
    }

    @Bean(name="mixContainerFactory")
    public SimpleRabbitListenerContainerFactory mixContainerFactory(){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setConcurrentConsumers(5);
        containerFactory.setMaxConcurrentConsumers(5);
        containerFactory.setPrefetchCount(5);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());

        rabbitListenerContainerFactoryConfigurer.configure(containerFactory,connectionFactory);
        return containerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:\tcorrelationData: {}\tack: {}\tcause: {}",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:\tmessage: {}\treplyCode: {}\treplyText: {}\texchange: {}\troutingKey: {}",message,replyCode,replyText,exchange,routingKey);
            }
        });
        return rabbitTemplate;
    }


}
