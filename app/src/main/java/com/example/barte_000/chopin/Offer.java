package com.example.barte_000.chopin;

import java.util.List;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class Offer {
    static List<Offer> l;
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
}
