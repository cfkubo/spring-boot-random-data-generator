package com.example.randomdatagenerator.util;

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
}