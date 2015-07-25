package com.winwang.myapplication;

/**
 * Created by Arbiter on 7/25/2015.
 */
public class Event {
    private String name = "Unnamed Event";
    private String description = "Without a description.";

    Event(){

    }
    Event(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
