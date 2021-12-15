package com.example.bikesstations;

import javax.persistence.*;

@Entity
@Table(name = "stations")
public class StationsBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private int available_bike_stands;
    private int available_bikes;
    private boolean status;
    private double lat;
    private double lng;

    public StationsBean(String name, String address, int available_bike_stands, int available_bikes, boolean status, double lat, double lng) {
        this.name = name;
        this.address = address;
        this.available_bike_stands = available_bike_stands;
        this.available_bikes = available_bikes;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
    }

    public StationsBean(String name) {
        this.name = name;
    }

    public StationsBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public int getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(int availableBikeStands) {
        this.available_bike_stands = availableBikeStands;
    }

    public int getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(int availableBikes) {
        this.available_bikes = availableBikes;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}