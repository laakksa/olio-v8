/* Tekijä: Saku Laakkonen
Pvm: 1.2.2020
 */
package com.olio.v8;

public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private double price;
    private double size;
    public Bottle(String name1, String manuf, double totE, double pri, double siz){
        name = name1;
        manufacturer = manuf;
        total_energy = totE;
        price = pri;
        size = siz;
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