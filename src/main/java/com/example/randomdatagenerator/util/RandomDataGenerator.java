// package com.example.randomdatagenerator.util;

// import java.sql.Date;
// import java.util.Random;

// public class RandomDataGenerator {

//     private static final String[] PRODUCTS = {"Laptop", "Smartphone", "Tablet", "Headphones", "Smartwatch","GOLD" ,"Gold Ring", "Diamond Necklace", "Leather Wallet", "Sunglasses", "Running Shoes", "Backpack", "T-shirt", "Jeans", "Dress", "Jacket", "Sweater", "Scarf", "Gloves", "Umbrella", "Socks","silver ring", "diamonds", "leather wallet", "sunglasses", "running shoes", "backpack", "t-shirt", "jeans", "dress", "jacket", "sweater", "scarf", "gloves", "umbrella", "socks"};
//     private static final String[] PAYMENT_METHODS = {"cash", "check", "credit", "debit","bitcoin","paypal","google pay","apple pay","amazon pay"};
//     private static final String[] SHIPPING_ADDRESSES = {
//         "123 Main St, Anytown, USA, 85281",
//         "456 Elm St, Othertown, USA, 45678",
//         "789 Oak St, Sometown, USA, 12345",
//         "123 Main St, Anytown, USA",
//         "456 Elm St, Othertown, USA",
//         "789 Oak St, Sometown, USA",
//         "111 chase street, Vegas, USA"
//     };
//     private static final Random RANDOM = new Random();
//     private static final String[] storeNames = {"Store A", "Store B", "Store C"};
//     private static final String[] storeAddresses = {"101 Store St", "202 Shop St", "303 Bazaar St"};
//     private static final String[] salesRepNames = {"John Doe", "Jane Smith", "Alice Johnson"};

//     public static String generateRandomProduct() {
//         return PRODUCTS[RANDOM.nextInt(PRODUCTS.length)];
//     }

//     public static double generateRandomPrice() {
//         return Math.round((10 + (1000 - 10) * RANDOM.nextDouble()) * 100.0) / 100.0;
//     }

//     public static int generateRandomQuantity() {
//         return RANDOM.nextInt(10) + 1; // Quantity between 1 and 10
//     }

//     public static String generateRandomShipTo() {
//         return "Address " + (RANDOM.nextInt(100) + 1); // Random address
//     }

//     public static String generateRandomPaymentMethod() {
//         return PAYMENT_METHODS[RANDOM.nextInt(PAYMENT_METHODS.length)];
//     }

//     public static String generateRandomShippingAddress() {
//         int index = RANDOM.nextInt(SHIPPING_ADDRESSES.length);
//         return SHIPPING_ADDRESSES[index];
//     }

//     public static Date generateRandomOrderDate() {
//         return new Date(0); // Generates current date for simplicity, can be modified for other logic
//     }

//     public static String generateRandomStoreName() {
//         return storeNames[new Random().nextInt(storeNames.length)];
//     }

//     public static String generateRandomStoreAddress() {
//         return storeAddresses[new Random().nextInt(storeAddresses.length)];
//     }

//     public static String generateRandomSalesRepName() {
//         return salesRepNames[new Random().nextInt(salesRepNames.length)];
//     }

// }


package com.example.randomdatagenerator.util;

import java.sql.Date;
import java.util.Random;

public class RandomDataGenerator {

    private static final Random RANDOM = new Random();

    public static String generateRandomProduct() {
        // String[] productTypes = {"Electronics", "Jewelry", "Clothing", "Accessories"};
        String[] productNames = {
                "Laptop", "Smartphone", "Tablet", "Headphones", "Smartwatch",
                "Gold Ring", "Diamond Necklace", "Leather Wallet", "Sunglasses",
                "Running Shoes", "Backpack", "T-shirt", "Jeans", "Dress", "Jacket",
                "Sweater", "Scarf", "Gloves", "Umbrella", "Socks",
                "silver ring", "diamonds"
        };
       // String productType = productTypes[RANDOM.nextInt(productTypes.length)];
        String productName = productNames[RANDOM.nextInt(productNames.length)];
       // return productType + " - " + productName; // Combine type and name for more variety
        return productName; 
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
        String[] paymentMethods = {"cash", "check", "credit", "debit", "bitcoin", "paypal", "google pay", "apple pay", "amazon pay"};
        return paymentMethods[RANDOM.nextInt(paymentMethods.length)];
    }

    public static String generateRandomShippingAddress() {
        String[] cities = {"Anytown", "Othertown", "Sometown", "Vegas", "New York", "Los Angeles", "Chicago"};
        String[] streets = {"Main St", "Elm St", "Oak St", "Chase Street", "Broadway", "Sunset Blvd", "Michigan Ave"};
        int streetNumber = RANDOM.nextInt(999) + 1;
        String city = cities[RANDOM.nextInt(cities.length)];
        String street = streets[RANDOM.nextInt(streets.length)];
        int zipCode = RANDOM.nextInt(90000) + 10000;
        return streetNumber + " " + street + ", " + city + ", USA, " + zipCode;
    }

    public static Date generateRandomOrderDate() {
        long now = System.currentTimeMillis();
        long randomTime = now - RANDOM.nextInt(365 * 24 * 60 * 60 * 1000); // Up to 1 year ago
        return new Date(randomTime);
    }

    public static String generateRandomStoreName() {
        String[] storePrefixes = {"Mega", "Super", "Quick", "Global", "Local"};
        String[] storeSuffixes = {"Mart", "Shop", "Store", "Bazaar", "Emporium"};
        String prefix = storePrefixes[RANDOM.nextInt(storePrefixes.length)];
        String suffix = storeSuffixes[RANDOM.nextInt(storeSuffixes.length)];
        return prefix + " " + suffix;
    }

    public static String generateRandomStoreAddress() {
        String[] streetNames = {"Market St", "Park Ave", "Pine Ln", "River Rd"};
        int streetNumber = RANDOM.nextInt(999) + 1;
        String street = streetNames[RANDOM.nextInt(streetNames.length)];
        String city = generateRandomCity();
        int zipCode = RANDOM.nextInt(90000) + 10000;
        return streetNumber + " " + street + ", " + city + ", USA, " + zipCode;
    }

    public static String generateRandomSalesRepName() {
        String[] firstNames = {"John", "Jane", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry"};
        String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        String firstName = firstNames[RANDOM.nextInt(firstNames.length)];
        String lastName = lastNames[RANDOM.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    private static String generateRandomCity(){
        String[] cities = {"Anytown", "Othertown", "Sometown", "Vegas", "New York", "Los Angeles", "Chicago"};
        return cities[RANDOM.nextInt(cities.length)];
    }
}