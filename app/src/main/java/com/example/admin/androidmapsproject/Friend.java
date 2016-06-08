package com.example.admin.androidmapsproject;

/**
 * Created by Mendez Soto on 6/7/2016.
 */
public class Friend {
    private int id;
    private String name;
    private int iconId;

    public Friend(String name, int iconId){
        this.iconId = iconId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }
}
