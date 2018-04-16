package com.example.tanshi.chefalong;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tanshi on 4/7/18.
 */

public class DishInfo {

    public String dish;
    public String cuisine;
    public int numGuests;
    public String timeText;
    public int duration;
    public HashMap<String,String> attendees;
    public double latitude;
    public double longitude;

    public DishInfo(){}

    public DishInfo(String dish, String cuisine, int numGuests, String timeText, int duration, HashMap<String,String>attendees, double latitude, double longitude) {
        this.dish = dish;
        this.cuisine = cuisine;
        this.numGuests = numGuests;
        this.timeText = timeText;
        this.duration = duration;
        this.attendees = attendees;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
