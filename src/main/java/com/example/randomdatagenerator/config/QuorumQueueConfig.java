package com.example.randomdatagenerator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuorumQueueConfig {

    public static final String QUORUM_QUEUE_NAME = "salesOrderQuorumQueue";

    @Bean
    public Queue quorumQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum");
        return new Queue(QUORUM_QUEUE_NAME, true, false, false, args);
    }
}