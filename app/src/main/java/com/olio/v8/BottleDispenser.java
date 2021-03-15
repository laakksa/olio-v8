/* Tekijä: Saku Laakkonen
Pvm: 1.2.2020
 */
package com.olio.v8;

import java.util.ArrayList;

public class BottleDispenser {
    private static BottleDispenser bd_instance  = null;
    private double money;
    ArrayList<Bottle> BottleList = new ArrayList<Bottle>();
    private BottleDispenser() {
        this.money = 0;
        //Add bottles ArrayListiin
        Bottle bottle1 = new Bottle("Pepsi Max", 1.80, 0.5);
        Bottle bottle6 = new Bottle("Pepsi Max", 1.80, 0.5);
        Bottle bottle2 = new Bottle("Pepsi Max", 2.20, 1.5);
        Bottle bottle3 = new Bottle("Coca-Cola Zero", 2.0, 0.5);
        Bottle bottle4 = new Bottle("Coca-Cola Zero", 2.5, 1.5);
        Bottle bottle5 = new Bottle("Fanta Zero", 1.95, 0.5);
        BottleList.add(bottle1);
        BottleList.add(bottle2);
        BottleList.add(bottle3);
        BottleList.add(bottle4);
        BottleList.add(bottle5);
        BottleList.add(bottle6);

    }
    //Singleton pattern for BottleDispenser
    public static BottleDispenser getInstance(){
        if (bd_instance == null){
            bd_instance = new BottleDispenser();
        }
        return bd_instance;
    }
    public double addMoney(double deposit) {
        this.money += deposit;
        return this.money;
    }

    public double removeMoney(double amount){
        this.money -= amount;
        return this.money;
    }

    public double getBalance(){
        return this.money;
    }

    //Returns money from the dispenser
    public void returnMoney() {
        money = 0;
    }
}



