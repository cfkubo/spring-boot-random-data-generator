package com.example.randomdatagenerator.util;

import java.sql.Date;
import java.util.Random;

public class RandomDataGenerator {

    private static final String[] PRODUCTS = {"Laptop", "Smartphone", "Tablet", "Headphones", "Smartwatch","GOLD" ,"Gold Ring", "Diamond Necklace", "Leather Wallet", "Sunglasses", "Running Shoes", "Backpack", "T-shirt", "Jeans", "Dress", "Jacket", "Sweater", "Scarf", "Gloves", "Umbrella", "Socks","silver ring", "diamonds", "leather wallet", "sunglasses", "running shoes", "backpack", "t-shirt", "jeans", "dress", "jacket", "sweater", "scarf", "gloves", "umbrella", "socks"};
    private static final String[] PAYMENT_METHODS = {"cash", "check", "credit", "debit"};
    private static final String[] SHIPPING_ADDRESSES = {
        "123 Main St, Anytown, USA",
        "456 Elm St, Othertown, USA",
        "789 Oak St, Sometown, USA",
        "123 Main St, Anytown, USA",
        "456 Elm St, Othertown, USA",
        "789 Oak St, Sometown, USA",
        "111 chase street, Vegas, USA"
    };
    private static final Random RANDOM = new Random();
    private static final String[] storeNames = {"Store A", "Store B", "Store C"};
    private static final String[] storeAddresses = {"101 Store St", "202 Shop St", "303 Bazaar St"};
    private static final String[] salesRepNames = {"John Doe", "Jane Smith", "Alice Johnson"};

    public static String generateRandomProduct() {
        return PRODUCTS[RANDOM.nextInt(PRODUCTS.length)];
    }

    public static double generateRandomPrice() {
        return Math.round((10 + (1000 - 10) * RANDOM.nextDouble()) * 100.0) / 100.0;
    }

    public static int generateRandomQuantity() {
        return RANDOM.nextInt(10) + 1; // Quantity between 1 and 10
    }

    public static String generateRandomShipTo() {
        return "Address " + (RANDOM.nextInt(100) + 1); // Random address
    }

    public static String generateRandomPaymentMethod() {
        return PAYMENT_METHODS[RANDOM.nextInt(PAYMENT_METHODS.length)];
    }

    public static String generateRandomShippingAddress() {
        int index = RANDOM.nextInt(SHIPPING_ADDRESSES.length);
        return SHIPPING_ADDRESSES[index];
    }

    public static Date generateRandomOrderDate() {
        return new Date(0); // Generates current date for simplicity, can be modified for other logic
    }

    public static String generateRandomStoreName() {
        return storeNames[new Random().nextInt(storeNames.length)];
    }

    public static String generateRandomStoreAddress() {
        return storeAddresses[new Random().nextInt(storeAddresses.length)];
    }

    public static String generateRandomSalesRepName() {
        return salesRepNames[new Random().nextInt(salesRepNames.length)];
    }

}