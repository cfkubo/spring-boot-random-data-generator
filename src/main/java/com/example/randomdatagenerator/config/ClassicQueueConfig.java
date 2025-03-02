package com.example.randomdatagenerator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClassicQueueConfig {
    public static final String CLASSIC_QUEUE_NAME = "salesOrderClassicQueue";

    @Bean
    public Queue classicQueue(){
        return new Queue(CLASSIC_QUEUE_NAME, true, false, false, null);
    }
}