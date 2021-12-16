package com.tpgroup.vitemontransport.model;

public class StationsBean {

    /*************************     ATTRIBUTS     ****************************/


    private long id;
    private String name;
    private String address;
    private int available_bike_stands;
    private int available_bikes;
    private double lat;
    private double lng;
    private boolean status;

    /*************************     CONSTRUCTEUR     ****************************/


    public StationsBean() {
    }

    public StationsBean(long id, String name, String address, int available_bike_stands, int available_bikes, double lat, double lng, boolean status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.available_bike_stands = available_bike_stands;
        this.available_bikes = available_bikes;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
    }

    /*************************     GETTER/SETTER     ****************************/


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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
