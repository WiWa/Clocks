package com.winwang.clocks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static com.winwang.clocks.DonutsArc.Ring.INNER;
import static com.winwang.clocks.DonutsArc.Ring.OUTER;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    final List<Event> eventsList = new ArrayList<>();
    private ListView eventsListView;
    private EventsAdapter mEventsAdapter;
    private DonutsVisualization mDonuts;
    private final BehaviorSubject<Date> dateToLookAt = BehaviorSubject.create(new Date());
    DateTime lastMidnight;
    DateTime noon;
    DateTime nextMidnight;

    static final long MS_IN_A_DAY = 86400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListView = (ListView) findViewById(R.id.eventsList);
        mDonuts = (DonutsVisualization) findViewById(R.id.dvClocks);

        // init events list
        eventsListView.setClickable(true);
        mEventsAdapter = new EventsAdapter(this, R.id.eventsList, eventsList);
        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
//                showToast(eventsList.get(position).getName() + ", " + String.valueOf(index));
            }
        });
        eventsListView.setEmptyView(findViewById(R.id.empty));
        eventsListView.setAdapter(mEventsAdapter);

        dateToLookAt.subscribe(new Action1<Date>() {
            @Override
            public void call(Date date) {
                refreshData(date);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                setDateToLookAt(dateToLookAt.getValue());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshData(Date date) {
        initTimes(date);
        Intent googleCalendarIntent = new Intent(MainActivity.this, GoogleCalendarQuickStart.class);
        googleCalendarIntent.putExtra("date", date.getTime());
        startActivityForResult(googleCalendarIntent, 0);
    }

    private void initTimes(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            clearAllEvents();

            ArrayList<String> colorIDs = data.getExtras().getStringArrayList("colorIDs");
            ArrayList<String> colors = data.getExtras().getStringArrayList("colors");
            Parcelable[] uncastevents = data.getExtras().getParcelableArray("events");

            for (Parcelable uncastEvent : uncastevents) {
                EventParcelable castEvent = (EventParcelable) uncastEvent;
                int colorIndex = colorIDs.indexOf(castEvent.getmColorID());
                Event event = new Event(castEvent);

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

            mEventsAdapter.notifyDataSetChanged();
            mDonuts.invalidate();
        }
    }

    private List<DonutsArc> arcsFromEvent(Event event) {
        if (event.isAllDay()) {
            return Collections.emptyList();
        }
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

    private void clearAllEvents() {
        eventsList.clear();
        mEventsAdapter.notifyDataSetChanged();
        mDonuts.clear();
    }

    public void setDateToLookAt(Date newDate) {
        this.dateToLookAt.onNext(newDate);
    }

    private Date addDays(Date today, int days){
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(today);
        c.add(java.util.Calendar.DATE, days);
        return c.getTime();
    }

    double msToDegree(double ms) {
        return (ms / MS_IN_A_DAY) * 360 * 2; // 12 hours clock
    }


}
