package com.winwang.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.services.calendar.model.*;

import java.util.List;

/**
 * Created by wiwa on 8/16/15.
 */
public class eventParcelable implements Parcelable{

    private String mSummary;
    private String mDescription;
    private String mStartTimeString;
    private String mEndTimeString;

    //// the data structure.
    public eventParcelable(com.google.api.services.calendar.model.Event event) {
        mSummary = event.getSummary();
        mDescription = event.getDescription();
        mStartTimeString = event.getStart().toString();
        if(event.isEndTimeUnspecified()){
            mEndTimeString = "";
        }
        else{
            mEndTimeString = event.getEnd().toString();
        }
    }

    private eventParcelable(Parcel in){
        mSummary = in.readString();
        mDescription = in.readString();
        mStartTimeString = in.readString();
        mEndTimeString = in.readString();
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
        p.writeString(mStartTimeString);
        p.writeString(mEndTimeString);
    }

    public String toString() {
        String myString = "eventParcelable{ ";
        myString += "Summary: " + mSummary;
        myString += "Description: " + mDescription;
        myString += "Start: " + mStartTimeString;
        if(mEndTimeString.length() > 0){
            myString += "End: " + mSummary;
        }
        else{
            myString += "End: " + "N/A";
        }
        myString += " }";
        return myString;
    }
}
