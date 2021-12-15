package com.example.bikesstations;

import javax.persistence.*;

public class BikesStationsBean {

    private String name;
    private String address;
    private int available_bike_stands;
    private int available_bikes;
    private String status;
    private LocationBean position;

    public BikesStationsBean(String name) {
        this.name = name;
    }

    public BikesStationsBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(int available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public int getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(int available_bikes) {
        this.available_bikes = available_bikes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationBean getPosition() {
        return position;
    }

    public void setPosition(LocationBean location) {
        this.position = location;
    }
}