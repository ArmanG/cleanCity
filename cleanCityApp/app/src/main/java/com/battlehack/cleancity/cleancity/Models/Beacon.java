package com.battlehack.cleancity.cleancity.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Vitaliy on 15-07-18.
 */
public class Beacon {

    private String id;
    private String name;
    private double latitude, longitude;
    private boolean full;
    private double distance;

    public Beacon () {
        this.name = "";
        this.latitude = 0;
        this.longitude = 0;
        this.id = "";
        this.distance = -1.0;
    }

    public Beacon (String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = "";
        this.distance = -1.0;
    }

    public static Beacon getBeaconFromJson(JSONObject jsonObject) throws JSONException {
        Beacon beacon = new Beacon();

        // gets data from a json object

        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            Log.d("BEACON", key);

            if (key.equals("_id")) {
                beacon.setId(jsonObject.getString(key));
            } else if (key.equals("name")) {
                beacon.setName(jsonObject.getString(key));
            } else if (key.equals("loc")) {
                JSONArray loc = jsonObject.getJSONArray("loc");

                if (loc != null) {
                    beacon.setLatitude(loc.getDouble(0));
                    beacon.setLongitude(loc.getDouble(1));
                }
            } else if (key.equals("full")) {
                beacon.setFull(jsonObject.getBoolean(key));
            } else if (key.equals("dist")) {
                beacon.setDistance(jsonObject.getDouble(key));
            }
        }

        return beacon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
