package com.winwang.myapplication;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arbiter on 7/25/2015.
 */
public class Event{

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    private String color;

    Event(){
        name = "Unnamed Event";
        description = "No Description";
        startDate = new Date();
        endDate = new Date();

        // Add one day.
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
    }

    Event(eventParcelable event){
        this(event, "#a4bdfc");
    }

    Event(eventParcelable event, String color){
        name = event.getmSummary();
        description = event.getmDescription();
        startDate = new Date(event.getmStartTime());
        endDate = new Date(event.getmEndTime());
        this.color = color;
    }

    public String toString(){
        String eventString = "Event{ ";

        eventString += "name: " + name + ", ";
        eventString += "description: " + description + ", ";
        eventString += "start: " + startDate.toString() + ", ";
        eventString += "end: " + endDate.toString() + ", ";
        eventString += "color: " + color + " }";

        return eventString;
    }

    public long msSinceMidnight(Date nowDate){
        Calendar c = Calendar.getInstance();
        c.setTime(nowDate);
        long now = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long passed = now - c.getTimeInMillis();

        return passed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }

    public String getColor() {
        return color;
    }
}
