package com.example.randomdatagenerator.scheduler;

import com.example.randomdatagenerator.service.SalesOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {

    private final SalesOrderService salesOrderService;

    public OrderScheduler(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @Scheduled(fixedRate = 1000)
    public void generateAndSendOrders() {
        salesOrderService.generateAndSendRandomOrders(10);
    }
}