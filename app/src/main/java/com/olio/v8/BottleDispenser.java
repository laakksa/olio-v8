/* Tekijä: Saku Laakkonen
Pvm: 1.2.2020
 */
package com.olio.v8;

import java.util.ArrayList;

public class BottleDispenser {
    private static BottleDispenser bd_instance  = null;
    private int bottles;
    private double money;
    ArrayList<Bottle> BottleList = new ArrayList<Bottle>();
    private BottleDispenser() {
        bottles = 5;
        this.money = 0;
        //Lisätään pullot ArrayListiin
        Bottle bottle1 = new Bottle("Pepsi Max", "Pepsi", 0.3, 1.80, 0.5);
        Bottle bottle2 = new Bottle("Pepsi Max", "Pepsi", 0.3, 2.20, 1.5);
        Bottle bottle3 = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 2.0, 0.5);
        Bottle bottle4 = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 2.5, 1.5);
        Bottle bottle5 = new Bottle("Fanta Zero", "Coca-Cola", 0.3, 1.95, 0.5);
        BottleList.add(bottle1);
        BottleList.add(bottle2);
        BottleList.add(bottle3);
        BottleList.add(bottle4);
        BottleList.add(bottle5);

    }
    public static BottleDispenser getInstance(){
        if (bd_instance == null){
            bd_instance = new BottleDispenser();
        }
        return bd_instance;
    }
    public double addMoney() {
        this.money += 1.00;
        return this.money;
    }
    //Ostaa pullon ja poistaa sen listasta, jos rahat ja pullot riittävät
    public void buyBottle(int index) {
        if(money >= BottleList.get(index-1).getPrice()) {
            if (bottles > 0) {
                bottles -= 1;
                money -= BottleList.get(index-1).getPrice();
                System.out.println("KACHUNK! " + BottleList.get(index-1).getName() + " came out of the dispenser!");
                removeBottle(index-1);
            } else {
                System.out.println("There are no more bottles left.");
                returnMoney();
            }
        } else{
            System.out.println("Add money first!");
        }
    }
    //Listaa koneessa olevat pullot
    public void listBottles(){
        for (int j = 0;  j < BottleList.size(); j++){
            System.out.println(j + 1 + ". Name: "  + BottleList.get(j).getName());
            System.out.println("\tSize: " + BottleList.get(j).getSize() + "\tPrice: " + BottleList.get(j).getPrice());
        }
    }
    //Poistaa koneesta ostetun pullon
    public void removeBottle(int index){
        BottleList.remove(index);
    }
    //Palauttaa rahat ja ilmoittaa palautetun rahan määrän
    public void returnMoney() {
        System.out.printf("Klink klink. Money came out! You got %.2f€ back%n", money);
        money = 0;
    }
}



