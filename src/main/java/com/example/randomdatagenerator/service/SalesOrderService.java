package com.example.randomdatagenerator.service;

import com.example.randomdatagenerator.model.SalesOrder;
import com.example.randomdatagenerator.util.RandomDataGenerator;
import com.example.randomdatagenerator.logging.LoggingService;
import com.rabbitmq.stream.Producer;
import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.ConfirmationHandler;
import com.rabbitmq.stream.ConfirmationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderService {

    private final Producer producer;
    private final LoggingService loggingService;

    @Autowired
    public SalesOrderService(Producer producer, LoggingService loggingService) {
        this.producer = producer;
        this.loggingService = loggingService;
    }

    public SalesOrder generateRandomOrder() {
        SalesOrder order = new SalesOrder();
        order.setProduct(RandomDataGenerator.generateRandomProduct());
        order.setPrice(RandomDataGenerator.generateRandomPrice());
        order.setQuantity(RandomDataGenerator.generateRandomQuantity());
        order.setShipTo(RandomDataGenerator.generateRandomShippingAddress());
        order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
        return order;
    }

    public void sendOrderToStream(SalesOrder order) {
        Message message = producer.messageBuilder()
                                  .addData(order.toString().getBytes())
                                  .build();
        producer.send(message, new ConfirmationHandler() {
            @Override
            public void handle(ConfirmationStatus status) {
                if (status.isConfirmed()) {
                    loggingService.logOrderDetails(
                        order.getProduct(),
                        order.getPrice(),
                        order.getQuantity(),
                        order.getShipTo(),
                        order.getPaymentMethod()
                    );
                } else {
                    // Handle error
                }
            }
        });
    }

    public void generateAndSendRandomOrders(int count) {
        for (int i = 0; i < count; i++) {
            SalesOrder order = generateRandomOrder();
            sendOrderToStream(order);
        }
    }

    // Custom listener method to handle incoming messages
    public void handleMessage(byte[] message) {
        String orderString = new String(message);
        System.out.println("Received message: " + orderString);
        // Additional processing can be done here
    }
}