package com.winwang.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.winwang.myapplication.DonutsArc.Ring.INNER;
import static com.winwang.myapplication.DonutsArc.Ring.OUTER;

public class MainActivity extends ActionBarActivity {

    final String TAG = "MainActivity";
    final List<Event> eventsList = new ArrayList<>();
    private ListView eventsListView;
    private EventsAdapter mEventsAdapter;
    private DonutsVisualization mDonuts;
    DateTime lastMidnight;
    DateTime noon;
    DateTime nextMidnight;

    static final long MS_IN_A_DAY = 86400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListView = (ListView) findViewById(R.id.EventsList);
        mDonuts = (DonutsVisualization) findViewById(R.id.dvClocks);

        // init events list
        eventsListView.setClickable(true);
        mEventsAdapter = new EventsAdapter(this, R.id.EventsList, eventsList);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
//                showToast(eventsList.get(position).getName() + ", " + String.valueOf(index));
            }
        });
        eventsListView.setAdapter(mEventsAdapter);

        // init donit visualization
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.set(java.util.Calendar.HOUR_OF_DAY, 0);
        c.set(java.util.Calendar.MINUTE, 0);
        c.set(java.util.Calendar.SECOND, 0);
        c.set(java.util.Calendar.MILLISECOND, 0);
        lastMidnight = new DateTime(c.getTime());
        c.set(java.util.Calendar.HOUR_OF_DAY, 12);
        noon = new DateTime(c.getTime());
        c.set(java.util.Calendar.HOUR_OF_DAY, 0);
        c.add(java.util.Calendar.DATE, 1);
        nextMidnight = new DateTime(c.getTime());

        Log.d(TAG, "Main Activity Created :3");

        getGoogleCalendarData();
    }

    private void getGoogleCalendarData() {
        Intent googleCalendarIntent = new Intent(MainActivity.this, GoogleCalendarQuickStart.class);
        startActivityForResult(googleCalendarIntent, 0);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*
        if (id == R.id.action_myitem) {
            Intent googleCalendarIntent = new Intent(MainActivity.this, GoogleCalendarQuickStart.class);
            startActivityForResult(googleCalendarIntent, GOOGLE_CALENDAR_EVENTS);
        }
        */

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<String> colorIDs = data.getExtras().getStringArrayList("colorIDs");
            ArrayList<String> colors = data.getExtras().getStringArrayList("colors");
            Parcelable[] uncastevents = data.getExtras().getParcelableArray("events");

            Log.d(TAG, "Google Events Received:  " + uncastevents);

            for (Parcelable uncastEvent : uncastevents) {
                eventParcelable castEvent = (eventParcelable) uncastEvent;
                int colorIndex = colorIDs.indexOf(castEvent.getmColorID());
                Event event = new Event(castEvent);

                // Only deal with events bound by today
                if (!isToday(event)) {
                    continue;
                }
                String eventColor = "None";
                if (colorIndex >= 0) {
                    eventColor = colors.get(colorIndex);
                    event.setColor(eventColor);
                }

                eventsList.add(event);
                for (DonutsArc arc : arcsFromEvent(event)) {
                    mDonuts.addArc(arc);
                }
            }

            String colorIDstring = "";
            for (String colorID : colorIDs) {
                colorIDstring += colorID + ", ";
            }

            String colorstring = "";
            for (String color : colors) {
                colorstring += color + ", ";
            }

            mEventsAdapter.notifyDataSetChanged();
            mDonuts.invalidate();
        }
    }

    private List<DonutsArc> arcsFromEvent(Event event) {
        double msStart = event.msSinceMidnight(event.getStartDate());
        double msEnd = event.msSinceMidnight(event.getEndDate());

        double msNoon = noon.getValue() - lastMidnight.getValue();

        DonutsArc inner = null;
        DonutsArc outer = null;

        double innerStart = msStart >= msNoon
                ? -1
                : msToDegree(msStart);
        double innerEnd = innerStart == -1
                ? -1
                : msToDegree(Math.min(msEnd, msNoon));
        double outerStart = msEnd <= msNoon
                ? -1
                : msToDegree(Math.max(msStart, msNoon));
        double outerEnd = outerStart == -1
                ? -1
                : msToDegree(msEnd);

        if (innerStart != -1) {
            inner = DonutsArc.create(mDonuts, innerStart, innerEnd,
                    Color.GRAY, Color.parseColor(event.getColor()), INNER);
        }
        if (outerStart != -1) {
            outer = DonutsArc.create(mDonuts, outerStart, outerEnd,
                    Color.GRAY, Color.parseColor(event.getColor()), OUTER);
        }

        return Arrays.asList(inner, outer);
    }

    double msToDegree(double ms) {
        return (ms / MS_IN_A_DAY) * 360 * 2; // 12 hours clock
    }

    private boolean isToday(Event event) {
        return event.getStartDate().getTime() > lastMidnight.getValue()
                && event.getEndDate().getTime() < nextMidnight.getValue();
    }


}
