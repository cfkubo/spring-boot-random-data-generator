// package com.example.randomdatagenerator.model;

// import java.util.Date;

// public class SalesOrder {
//     private String product;
//     private double price;
//     private int quantity;
//     private String shipTo;
//     private String paymentMethod;
//     private Date orderDate;
//     private String address;

//     public String getProduct() {
//         return product;
//     }

//     public void setProduct(String product) {
//         this.product = product;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public void setPrice(double price) {
//         this.price = price;
//     }

//     public int getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }

//     public String getShipTo() {
//         return shipTo;
//     }

//     public void setShipTo(String shipTo) {
//         this.shipTo = shipTo;
//     }

//     public String getPaymentMethod() {
//         return paymentMethod;
//     }

//     public void setPaymentMethod(String paymentMethod) {
//         this.paymentMethod = paymentMethod;
//     }

//     public Date getOrderDate() {
//         return orderDate;
//     }

//     public void setOrderDate(Date orderDate) {
//         this.orderDate = orderDate;
//     }
//     public String getAddress() {
//         return address;
//     }

//     public void setAddress(String address) {
//         this.address = address;
//     }

//     @Override
//     public String toString() {
//         return "SalesOrder{" +
//                 "product='" + product + '\'' +
//                 ", price=" + price +
//                 ", quantity=" + quantity +
//                 ", shipTo='" + shipTo + '\'' +
//                 ", paymentMethod='" + paymentMethod + '\'' +
//                 ", orderDate=" + orderDate +
//                 ", address='" + address + '\'' +
//                 '}';
//     }
// }

package com.example.randomdatagenerator.model;

import java.util.Date;

public class SalesOrder {
    private String product;
    private double price;
    private int quantity;
    private String shipTo;
    private String paymentMethod;
    private Date orderDate;
    private String address;
    private String storeName;
    private String storeAddress;
    private String salesRepName;

    // Getters and setters for all fields
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getShipTo() { return shipTo; }
    public void setShipTo(String shipTo) { this.shipTo = shipTo; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreAddress() { return storeAddress; }
    public void setStoreAddress(String storeAddress) { this.storeAddress = storeAddress; }

    public String getSalesRepName() { return salesRepName; }
    public void setSalesRepName(String salesRepName) { this.salesRepName = salesRepName; }

    @Override
    public String toString() {
        return "SalesOrder{" +
                "product='" + product + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", shipTo='" + shipTo + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderDate=" + orderDate +
                ", address='" + address + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", salesRepName='" + salesRepName + '\'' +
                '}';

    // @Override
    // public String toString() {
    //     return "SalesOrder{" +
    //             "product='" + product + '\'' +
    //             ", price=" + price +
    //             ", quantity=" + quantity +
    //             ", shipTo='" + shipTo + '\'' +
    //             ", paymentMethod='" + paymentMethod + '\'' +
    //             ", orderDate=" + orderDate +
    //             ", address='" + address + '\'' +
    //             '}';
    }
}