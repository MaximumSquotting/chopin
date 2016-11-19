package com.example.barte_000.chopin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class Offer {
    static ArrayList<Offer> l;
    String name;
    String address;
    int price;
    int MaxLimit;

    Offer(String name, String address, int price, int MaxLimit){
        this.name = name;
        this.address = address;
        this.price = price;
        this.MaxLimit = MaxLimit;
        AddToList(this);
    }

    private void AddToList(Offer o){
        l.add(o);
    }

    private void initializeData(){
        l = new ArrayList<>();
        l.add(new Offer("Emma Wilson", "23 years old", 1,1));
        l.add(new Offer("Lavery Maiss", "25 years old", 2,2));
        l.add(new Offer("Lillie Watts", "35 years old", 3,3));
    }
}
