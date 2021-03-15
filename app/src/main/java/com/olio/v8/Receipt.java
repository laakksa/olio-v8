package com.olio.v8;

public class Receipt {
    private String item;
    private double price;
    public Receipt(String item, double price){
        this.item = item;
        this.price = price;
    }
    public String getItem(){ return this.item; }
    public double getPrice(){ return this.price;}
}
