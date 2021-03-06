package com.chopin.chopin.models;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

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
    private Date offer_date;

    public Date getOfferDate() {
        return offer_date;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOffer_date(String data) {
        offer_date = parseData(data);
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

    public Integer getId() {
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

    public Location getLocation()
    {
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Offer(Integer id, String name, String address, String description, int cost_per_person, int max_number_people, String data, LatLng latLng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.cost_per_person = cost_per_person;
        this.max_number_of_people = max_number_people;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.offer_date = parseData(data);
    }

    Offer() {
    }
    private void AddToList(Offer o) {
        l.add(o);
    }
    public String[] parse(){
        String[] l = {this.getName(), this.getDescription(), this.getAddress(), String.valueOf(this.getCost_per_person()), String.valueOf(this.getMax_number_of_people()),this.getOfferDate().toString() };
        //l.add(this.getName());
        //l.add(this.getDescription());
        //l.add(this.getAddress());
        //l.add(String.valueOf(this.getCost_per_person()));
        //l.add(String.valueOf(this.getMax_number_of_people()));
        //l.add(this.getOfferDate().toString());
        return l;
    }
}
