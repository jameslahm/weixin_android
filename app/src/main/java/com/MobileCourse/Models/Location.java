package com.MobileCourse.Models;

import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

public class Location {
    double longitude;

    double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Location fromString(String value){
        Type listType = new TypeToken<Location>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    public static Location fromLatLng(LatLng position){
        return new Location(position.longitude,position.latitude);
    }
}
