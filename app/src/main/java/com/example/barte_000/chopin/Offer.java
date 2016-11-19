package com.example.barte_000.chopin;

import java.util.ArrayList;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class Offer {
    static ArrayList<Offer> l;
    String name;
    String description;
    String address;
    int cost_per_person;
    int max_number_people;

    Offer(String name, String address, String description, int cost_per_person, int max_number_people){
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_people = max_number_people;
    }

    Offer(){}

    private void AddToList(Offer o){
        l.add(o);
    }



    public void initializeData(){
        l = new ArrayList<>();
        l.add(new Offer("Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer("Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer("Ania", "Metalowa", "Frytki",5,6));
        l.add(new Offer("Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer("Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer("Ania", "Metalowa", "Frytki",5,6));
        l.add(new Offer("Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer("Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer("Ania", "Metalowa", "Frytki",5,6));
    }
}
