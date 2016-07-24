package com.winwang.clocks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by wiwa on 8/16/15.
 */
public class EventParcelable implements Parcelable{

    private String mSummary;
    private String mDescription;
    private long mStartTime;
    private long mEndTime;

    private String mColorID;

    //// the data structure.
    public EventParcelable(com.google.api.services.calendar.model.Event event) {
        mSummary = event.getSummary();
        if(event.getDescription() != null){
            mDescription = event.getDescription();
        }
        else{
            mDescription = "";
        }
        if(event.getStart().getDateTime() != null){
            mStartTime = event.getStart().getDateTime().getValue();
        }
        else if(event.getStart().getDate() != null){
            mStartTime = event.getStart().getDate().getValue();
        }
        else{
            mStartTime = 0;
        }
        if(event.getEnd().getDateTime() != null){
            mEndTime = event.getEnd().getDateTime().getValue();
        }
        else if(event.getEnd().getDate() != null){
            mEndTime = event.getEnd().getDate().getValue();
        }
        else{
            mEndTime = 0;
        }
        mColorID = event.getColorId();
        if(mColorID == null){
            mColorID = "1";
        }
//        if(event.isEndTimeUnspecified()){
//            mEndTime = 0;
//        }
//        else{
//            mEndTime = event.getEnd().getDate().getValue();
//        }
    }

    private EventParcelable(Parcel in) {
        mSummary = in.readString();
        mDescription = in.readString();
        mStartTime = in.readLong();
        mEndTime = in.readLong();
        mColorID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<EventParcelable> CREATOR =
            new Parcelable.Creator<EventParcelable>() {
                public EventParcelable createFromParcel(Parcel in) {
                    return new EventParcelable(in);
                }
                public EventParcelable[] newArray(int size) {
                    return new EventParcelable[size];
                }
            };

    public void writeToParcel(Parcel p, int flag) {
        p.writeString(mSummary);
        p.writeString(mDescription);
        p.writeLong(mStartTime);
        p.writeLong(mEndTime);
        p.writeString(mColorID);
    }

    public String toString() {
        String myString = "EventParcelable{ ";
        myString += "Summary: " + mSummary;
        myString += "Description: " + mDescription;
        myString += "Start: " + (new Date(mStartTime)).toString() + ", " + String.valueOf(mStartTime);
        if(mEndTime > 0){
            myString += "End: " + (new Date(mEndTime)).toString() + ", " + String.valueOf(mEndTime);
        }
        else{
            myString += "End: " + "N/A";
        }
        myString += "ColorID: " + mColorID;
        myString += " }";
        return myString;
    }

    public String getmSummary() {
        return mSummary;
    }

    public String getmDescription() {
        return mDescription;
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public String getmColorID() {
        return mColorID;
    }

}
