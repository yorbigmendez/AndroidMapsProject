package com.example.admin.androidmapsproject;

/**
 * Created by Mendez Soto on 5/31/2016.
 */
public class Route {
    private String routeName;
    public Route(String r){
        routeName = r;
    }

    public Route(){}


    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String r){
        this.routeName = r;
    }
}
