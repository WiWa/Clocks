package com.winwang.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by wiwa on 8/16/15.
 */
public class eventParcelable implements Parcelable{

    private String mSummary;
    private String mDescription;
    private long mStartTime;
    private long mEndTime;

    //// the data structure.
    public eventParcelable(com.google.api.services.calendar.model.Event event) {
        mSummary = event.getSummary();
        mDescription = event.getDescription();
        mStartTime = event.getStart().getDateTime().getValue();
        if(event.isEndTimeUnspecified()){
            mEndTime = 0;
        }
        else{
            mEndTime = event.getEnd().getDateTime().getValue();
        }
    }

    private eventParcelable(Parcel in){
        mSummary = in.readString();
        mDescription = in.readString();
        mStartTime = in.readLong();
        mEndTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<eventParcelable> CREATOR =
            new Parcelable.Creator<eventParcelable>() {
                public eventParcelable createFromParcel(Parcel in) {
                    return new eventParcelable(in);
                }
                public eventParcelable[] newArray(int size) {
                    return new eventParcelable[size];
                }
            };

    public void writeToParcel(Parcel p, int flag) {
        p.writeString(mSummary);
        p.writeString(mDescription);
        p.writeLong(mStartTime);
        p.writeLong(mEndTime);
    }

    public String toString() {
        String myString = "eventParcelable{ ";
        myString += "Summary: " + mSummary;
        myString += "Description: " + mDescription;
        myString += "Start: " + (new Date(mStartTime)).toString() + ", " + String.valueOf(mStartTime);
        if(mEndTime > 0){
            myString += "End: " + (new Date(mEndTime)).toString() + ", " + String.valueOf(mEndTime);
        }
        else{
            myString += "End: " + "N/A";
        }
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

}
