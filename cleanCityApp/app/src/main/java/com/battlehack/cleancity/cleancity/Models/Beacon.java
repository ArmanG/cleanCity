package com.battlehack.cleancity.cleancity.Models;

/**
 * Created by Vitaliy on 15-07-18.
 */
public class Beacon {

    private int id;
    private String name;
    private double latitude, longitude;
    private boolean full;
    private double distance;

    public Beacon (String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = 0;
        this.distance = -1.0;
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

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public double isDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
