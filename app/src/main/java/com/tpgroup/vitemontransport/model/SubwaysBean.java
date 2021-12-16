package com.tpgroup.vitemontransport.model;

public class SubwaysBean {

    /*************************     ATTRIBUTS     ****************************/

    private String id;
    private String nom;
    private String couvert;
    private String ligne;
    private String commune;
    private Double geoPointLong;
    private Double geoPointLat;
    private String type;

    /*************************     CONSTRUCTEUR     ****************************/

    public SubwaysBean() {
    }

    public SubwaysBean(String nom, String ligne, Double geoPointLong, Double geoPointLat, String type) {
        this.nom = nom;
        this.ligne = ligne;
        this.geoPointLong = geoPointLong;
        this.geoPointLat = geoPointLat;
        this.type = type;
    }

    /*************************     GETTER/SETTER     ****************************/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouvert() {
        return couvert;
    }

    public void setCouvert(String couvert) {
        this.couvert = couvert;
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public Double getGeoPointLong() {
        return geoPointLong;
    }

    public void setGeoPointLong(Double geoPointLong) {
        this.geoPointLong = geoPointLong;
    }

    public Double getGeoPointLat() {
        return geoPointLat;
    }

    public void setGeoPointLat(Double geoPointLat) {
        this.geoPointLat = geoPointLat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
