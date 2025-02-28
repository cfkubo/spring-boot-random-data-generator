package com.example.randomdatagenerator.model;

public class SalesOrder {
    private String product;
    private double price;
    private int quantity;
    private String shipTo;
    private String paymentMethod;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
                "product='" + product + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", shipTo='" + shipTo + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}