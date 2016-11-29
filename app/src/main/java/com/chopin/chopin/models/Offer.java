package com.chopin.chopin.models;

import java.util.ArrayList;

public class Offer {
    static ArrayList<Offer> l;
    int id;
    String name;
    private String description;
    String address;
    int cost_per_person;
    int max_number_of_people;
    private double latitude = 1;
    private double longitude = 1;

    public static ArrayList<Offer> getL() {
        return l;
    }

    public static void setL(ArrayList<Offer> l) {
        Offer.l = l;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCost_per_person() {
        return cost_per_person;
    }

    public void setCost_per_person(int cost_per_person) {
        this.cost_per_person = cost_per_person;
    }

    public int getMax_number_of_people() {
        return max_number_of_people;
    }

    public void setMax_number_of_people(int max_number_of_people) {
        this.max_number_of_people = max_number_of_people;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Offer(int id, String name, String address, String description, int cost_per_person, int max_number_people) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
        latitude = 15;
        longitude = 30;
    }

    public Offer(String name, String address, String description, int cost_per_person, int max_number_people) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
    }

    Offer() {
    }

    private void AddToList(Offer o) {
        l.add(o);
    }


    public void initializeData() {
        l = new ArrayList<>();
        l.add(new Offer(1, "Tomek", "Grunwald", "Schabowe", 10, 4));
        l.add(new Offer(2, "Kasia", "Ogrodowa", "Pizaa", 15, 3));
        l.add(new Offer(3, "Ania", "Metalowa", "Frytki", 5, 6));
        l.add(new Offer(4, "Tomek", "Grunwald", "Schabowe", 10, 4));
        l.add(new Offer(5, "Kasia", "Ogrodowa", "Pizaa", 15, 3));
        l.add(new Offer(6, "Ania", "Metalowa", "Frytki", 5, 6));
        l.add(new Offer(7, "Tomek", "Grunwald", "Schabowe", 10, 4));
        l.add(new Offer(8, "Kasia", "Ogrodowa", "Pizaa", 15, 3));
        l.add(new Offer(9, "Ania", "Metalowa", "Frytki", 5, 6));
    }
}
