package com.winwang.myapplication;

import android.os.AsyncTask;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.*;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class ApiAsyncTask extends AsyncTask<Object, Void, Void> {
    private GoogleCalendarQuickStart mActivity;

    /**
     * Constructor.
     * @param activity GoogleCalendarQuickStart that spawned this task.
     */
    ApiAsyncTask(GoogleCalendarQuickStart activity) {
        this.mActivity = activity;
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected Void doInBackground(Object... params) {
        HttpTransport transport = (HttpTransport) params[0];
        JsonFactory jsonFactory = (JsonFactory) params[1];
        GoogleAccountCredential credential = (GoogleAccountCredential) params[2];
        try {
            mActivity.clearResultsText();
            mActivity.updateResultsText(getDataFromApi());

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());

        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    GoogleCalendarQuickStart.REQUEST_AUTHORIZATION);

        } catch (Exception e) {
            mActivity.updateStatus("The following error occurred:\n" +
                    e.getMessage());
        }

        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar
                .Calendar.Builder(transport, jsonFactory, credential)
                .setApplicationName("applicationName").build();

// Retrieve color definitions for calendars and events
        Colors colors = null;
        try {
            colors = service.colors().get().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//// Print available calendar list entry colors
//        for (Map.Entry<String, ColorDefinition> color : colors.getCalendar().entrySet()) {
//            System.out.println("ColorId : " + color.getKey());
//            System.out.println("  Background: " + color.getValue().getBackground());
//            System.out.println("  Foreground: " + color.getValue().getForeground());
//        }
//
//// Print available event colors
//        for (Map.Entry<String, ColorDefinition> color : colors.getEvent().entrySet()) {
//            System.out.println("ColorId : " + color.getKey());
//            System.out.println("  Background: " + color.getValue().getBackground());
//            System.out.println("  Foreground: " + color.getValue().getForeground());
//        }

        mActivity.setColors(colors);

        return null;
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private List<String> getDataFromApi() throws IOException {
        // List the next 10 events from the primary calendar.
        //DateTime now = new DateTime(System.currentTimeMillis());
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.set(java.util.Calendar.HOUR_OF_DAY, 0);
        c.set(java.util.Calendar.MINUTE, 0);
        c.set(java.util.Calendar.SECOND, 0);
        c.set(java.util.Calendar.MILLISECOND, 0);
        DateTime lastMidnight = new DateTime(c.getTime());
        c.add(java.util.Calendar.DATE, 1);
        DateTime nextMidnight = new DateTime(c.getTime());


        List<String> eventStrings = new ArrayList<String>();
        Events events = mActivity.mService.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(lastMidnight)
                //.setTimeMax(nextMidnight)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<com.google.api.services.calendar.model.Event> items = events.getItems();

        mActivity.setEvents(items);

        for (com.google.api.services.calendar.model.Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                // All-day events don't have start times, so just use
                // the start date.
                start = event.getStart().getDate();
            }
            eventStrings.add(
                    String.format("%s (%s)", event.getSummary(), start));
        }
        return eventStrings;
    }

}