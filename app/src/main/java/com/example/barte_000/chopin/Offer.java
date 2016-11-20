package com.example.barte_000.chopin;
import java.util.ArrayList;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class Offer {
    static ArrayList<Offer> l;
    int id;
    String name;
    String description;
    String address;
    int cost_per_person;
    int max_number_of_people;
    double lattitude = 1;
    double longitude = 1;

    Offer(int id, String name, String address, String description, int cost_per_person, int max_number_people){
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
        lattitude = 15;
        longitude = 30;
    }

    Offer(String name, String address, String description, int cost_per_person, int max_number_people){
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
    }

    Offer(){}

    private void AddToList(Offer o){
        l.add(o);
    }



    public void initializeData(){
        l = new ArrayList<>();
        l.add(new Offer(1,"Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer(2,"Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer(3,"Ania", "Metalowa", "Frytki",5,6));
        l.add(new Offer(4,"Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer(5,"Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer(6,"Ania", "Metalowa", "Frytki",5,6));
        l.add(new Offer(7,"Tomek", "Grunwald", "Schabowe",10,4));
        l.add(new Offer(8,"Kasia", "Ogrodowa", "Pizaa",15,3));
        l.add(new Offer(9,"Ania", "Metalowa", "Frytki",5,6));
    }
}
