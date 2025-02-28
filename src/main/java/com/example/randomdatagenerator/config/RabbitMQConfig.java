package com.example.randomdatagenerator.config;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.Producer;
import com.rabbitmq.stream.StreamException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.randomdatagenerator.service.SalesOrderService;

@Configuration
public class RabbitMQConfig {

    public static final String STREAM_NAME = "salesOrderStream";

    // @Bean
    // public Environment rabbitmqEnvironment() {
    //     return Environment.builder()
    //             .host("localhost")
    //             .port(5552) // Ensure this is the correct port for RabbitMQ Streams
    //             .build();
    // }

    @Bean
    public Producer salesOrderProducer(Environment environment) {
        try {
            environment.streamCreator().stream(STREAM_NAME).create();
        } catch (StreamException e) {
            // Stream already exists
        }
        return environment.producerBuilder().stream(STREAM_NAME).build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                  MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(STREAM_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(SalesOrderService salesOrderService) {
        return new MessageListenerAdapter(salesOrderService, "handleMessage");
    }
}