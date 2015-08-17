package com.winwang.myapplication;

import java.util.Date;

/**
 * Created by Arbiter on 7/25/2015.
 */
public class Event{

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    Event(eventParcelable event){
        name = event.getmSummary();
        description = event.getmDescription();
        startDate = new Date(event.getmStartTime());
        endDate = new Date(event.getmEndTime());
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

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
}
