package com.chopin.chopin.models;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Offer {
    private static ArrayList<Offer> l;
    private Integer id;
    private String name;
    private String description;
    private String address;
    private int cost_per_person;
    private int max_number_of_people;
    private double latitude = 1;
    private double longitude = 1;
    Date OfferDate;

    public Date getOfferDate() {
        return OfferDate;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOfferDate(String data) {
        OfferDate = parseData(data);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Date parseData(String data){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date d = format.parse(data);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Offer(String name, String address, String description, int cost_per_person, int max_number_people, String data) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
        this.latitude = 15;
        this.longitude = 30;
        this.OfferDate = parseData(data);
    }

    Offer() {
    }
    private void AddToList(Offer o) {
        l.add(o);
    }
}
