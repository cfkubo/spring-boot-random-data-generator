package com.example.randomdatagenerator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuorumQueueConfig {

    public static final String QUORUM_QUEUE_NAME = "salesOrderQuorumQueue";

    @Bean
    public Queue quorumQueue() {
        return new Queue(QUORUM_QUEUE_NAME, true, false, false, null);
    }
}