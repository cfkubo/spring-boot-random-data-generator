// package com.example.randomdatagenerator.service;

// import com.example.randomdatagenerator.model.SalesOrder;
// import com.example.randomdatagenerator.util.RandomDataGenerator;
// // import com.example.randomdatagenerator.logging.LoggingService;
// import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.Connection;
// import com.rabbitmq.client.ConnectionFactory;
// import com.rabbitmq.client.MessageProperties;
// import com.rabbitmq.stream.Producer;
// import com.rabbitmq.stream.Message;
// import com.rabbitmq.stream.ConfirmationHandler;
// import com.rabbitmq.stream.ConfirmationStatus;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import java.io.IOException;
// import java.sql.Date;
// import java.util.concurrent.TimeoutException;

// @Service
// public class SalesOrderService {

//     private final Producer streamProducer;
//     // private final LoggingService loggingService;

//     @Value("${spring.rabbitmq.host}")
//     private String rabbitmqHost;

//     @Value("${spring.rabbitmq.port}")
//     private int rabbitmqPort;

//     @Value("${spring.rabbitmq.username}")
//     private String rabbitmqUsername;

//     @Value("${spring.rabbitmq.password}")
//     private String rabbitmqPassword;

//  private static final String STREAM_QUEUE_NAME = "salesOrderStreamQueue";
//     private static final String QUORUM_QUEUE_NAME = "salesOrderQuorumQueue";
//     private static final String CLASSIC_QUEUE_NAME = "salesOrderClassicQueue";

//     // public SalesOrderService(Producer streamProducer, LoggingService loggingService) {
//     //     this.streamProducer = streamProducer;
//     //     // this.loggingService = loggingService;
//     // }

//     public SalesOrderService(Producer streamProducer) {
//         this.streamProducer = streamProducer;
//         // this.loggingService = loggingService;
//     }


//     public SalesOrder generateRandomOrder() {
//         SalesOrder order = new SalesOrder();
//         order.setProduct(RandomDataGenerator.generateRandomProduct());
//         order.setPrice(RandomDataGenerator.generateRandomPrice());
//         order.setQuantity(RandomDataGenerator.generateRandomQuantity());
//         order.setShipTo(RandomDataGenerator.generateRandomShippingAddress());
//         order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
//         return order;
//     }
    
//     public void sendOrderToStream(SalesOrder order) {
//         sendOrderToQueue(order, STREAM_QUEUE_NAME);
//     }

//     // public void sendOrderToStream(SalesOrder order) {
//     //     Message message = streamProducer.messageBuilder()
//     //             .addData(order.toString().getBytes())
//     //             .build();
//     //     streamProducer.send(message, new ConfirmationHandler() {
//     //         @Override
//     //         public void handle(ConfirmationStatus status) {
//     //             // if (status.isConfirmed()) {
//     //             //     loggingService.logOrderDetails(
//     //             //             order.getProduct(),
//     //             //             order.getPrice(),
//     //             //             order.getQuantity(),
//     //             //             order.getShipTo(),
//     //             //             order.getPaymentMethod()
//     //             //     );
//     //             // } else {
//     //             //     // Handle error
//     //             // }
//     //         }
//     //     });
//     // }

//     public void sendOrderToQuorumQueue(SalesOrder order) {
//         sendOrderToQueue(order, QUORUM_QUEUE_NAME);
//     }

//     public void sendOrderToClassicQueue(SalesOrder order) {
//         sendOrderToQueue(order, CLASSIC_QUEUE_NAME);
//     }

//     private void sendOrderToQueue(SalesOrder order, String queueName) {
//         ConnectionFactory factory = new ConnectionFactory();
//         factory.setHost(rabbitmqHost);
//         factory.setPort(rabbitmqPort);
//         factory.setUsername(rabbitmqUsername);
//         factory.setPassword(rabbitmqPassword);

//         try (Connection connection = factory.newConnection();
//              Channel channel = connection.createChannel()) {

//             channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, order.toString().getBytes());

//         } catch (IOException | TimeoutException e) {
//             e.printStackTrace();
//         }
//     }

//     public void generateAndSendRandomOrders(int count) {
//         for (int i = 0; i < count; i++) {
//             SalesOrder order = new SalesOrder();
//             order.setProduct(RandomDataGenerator.generateRandomProduct());
//             order.setPrice(RandomDataGenerator.generateRandomPrice());
//             order.setQuantity(RandomDataGenerator.generateRandomQuantity());
//             order.setShipTo(RandomDataGenerator.generateRandomShippingAddress());
//             order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
//             order.setAddress(RandomDataGenerator.generateRandomShippingAddress());
//             order.setOrderDate(new Date(i));

//             sendOrderToStream(order);
//             sendOrderToQuorumQueue(order);
//             sendOrderToClassicQueue(order);
//         }
//     }
//             order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
//             order.setAddress(RandomDataGenerator.generateRandomShippingAddress());
//             order.setOrderDate(new Date());

//             // sendOrderToStream(order);
//             void sendOrderToQuorumQueue(order);
//             sendOrderToClassicQueue(order);
//         }
//     }

//     public void handleMessage(byte[] message) {
//         String orderString = new String(message);
//         System.out.println("Received message: " + orderString);
//     }
// }

package com.example.randomdatagenerator.service;

import com.example.randomdatagenerator.model.SalesOrder;
import com.example.randomdatagenerator.util.RandomDataGenerator;
import com.example.randomdatagenerator.logging.LoggingService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.stream.Producer;
import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.ConfirmationHandler;
import com.rabbitmq.stream.ConfirmationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class SalesOrderService {

    private final Producer streamProducer;
    // private final LoggingService loggingService;

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    private static final String QUORUM_QUEUE_NAME = "salesOrderQuorumQueue";
    private static final String CLASSIC_QUEUE_NAME = "salesOrderClassicQueue";

    // public SalesOrderService(Producer streamProducer, LoggingService loggingService) {
    //     this.streamProducer = streamProducer;
    //     this.loggingService = loggingService;
    // }

    public SalesOrderService(Producer streamProducer) {
        this.streamProducer = streamProducer;
    }


    // public SalesOrder generateRandomOrder() {
    //     SalesOrder order = new SalesOrder();
    //     order.setProduct(RandomDataGenerator.generateRandomProduct());
    //     order.setPrice(RandomDataGenerator.generateRandomPrice());
    //     order.setQuantity(RandomDataGenerator.generateRandomQuantity());
    //     order.setShipTo(RandomDataGenerator.generateRandomShippingAddress());
    //     order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
    //     return order;
    // }

    public SalesOrder generateRandomOrder() {
        SalesOrder order = new SalesOrder();
        order.setProduct(RandomDataGenerator.generateRandomProduct());
        order.setPrice(RandomDataGenerator.generateRandomPrice());
        order.setQuantity(RandomDataGenerator.generateRandomQuantity());
        order.setShipTo(RandomDataGenerator.generateRandomShipTo());
        order.setPaymentMethod(RandomDataGenerator.generateRandomPaymentMethod());
        order.setAddress(RandomDataGenerator.generateRandomShippingAddress());
        order.setOrderDate(RandomDataGenerator.generateRandomOrderDate());
        order.setStoreName(RandomDataGenerator.generateRandomStoreName());
        order.setStoreAddress(RandomDataGenerator.generateRandomStoreAddress());
        order.setSalesRepName(RandomDataGenerator.generateRandomSalesRepName());

        //loggingService.logOrderDetails(order);
        return order;
    }

    // public void sendOrderToStream(SalesOrder order) {
    //     Message message = streamProducer.messageBuilder()
    //             .addData(order.toString().getBytes())
    //             .build();
    //     streamProducer.send(message, new ConfirmationHandler() {
    //         @Override
    //         public void handle(ConfirmationStatus status) {
    //             if (status.isConfirmed()) {
    //                 loggingService.logOrderDetails(
    //                         order.getProduct(),
    //                         order.getPrice(),
    //                         order.getQuantity(),
    //                         order.getShipTo(),
    //                         order.getPaymentMethod()
    //                 );
    //             } else {
    //                 // Handle error
    //             }
    //         }
    //     });
    // }

    public void sendOrderToStream(SalesOrder order) {
        Message message = streamProducer.messageBuilder()
                .addData(order.toString().getBytes())
                .build();
        streamProducer.send(message, new ConfirmationHandler() {
            @Override
            public void handle(ConfirmationStatus status) {
                if (status.isConfirmed()) {
                    // Order sent successfully. Perform any necessary post-send operations here.
                    // For example, update order status in a database, etc.
                } else {
                    // Handle error: message was not confirmed.
                    // You might want to log the error, retry, or take other appropriate actions.
                    System.err.println("Error sending order to stream. Confirmation status: " + status);
                }
            }
        });
    }
    
    public void sendOrderToQuorumQueue(SalesOrder order) {
        sendOrderToQueue(order, QUORUM_QUEUE_NAME);
    }

    public void sendOrderToClassicQueue(SalesOrder order) {
        sendOrderToQueue(order, CLASSIC_QUEUE_NAME);
    }

    private void sendOrderToQueue(SalesOrder order, String queueName) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqHost);
        factory.setPort(rabbitmqPort);
        factory.setUsername(rabbitmqUsername);
        factory.setPassword(rabbitmqPassword);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, order.toString().getBytes());

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void generateAndSendRandomOrders(int count) {
        for (int i = 0; i < count; i++) {
            SalesOrder order = generateRandomOrder();
            sendOrderToStream(order);
            sendOrderToQuorumQueue(order);
            sendOrderToClassicQueue(order);
        }
    }

    public void handleMessage(byte[] message) {
        String orderString = new String(message);
        System.out.println("Received message: " + orderString);
    }
}