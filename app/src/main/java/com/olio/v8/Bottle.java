/* Tekijä: Saku Laakkonen
Pvm: 1.2.2020
 */
package com.olio.v8;

public class Bottle {
    private String name;
    private double price;
    private double size;
    public Bottle(String name, double price, double size){
        this.name = name;
        this.price = price;
        this.size = size;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public double getSize(){
        return size;
    }
}