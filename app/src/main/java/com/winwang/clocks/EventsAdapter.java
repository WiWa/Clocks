package com.winwang.clocks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Arbiter on 7/25/2015.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    private List<Event> events;
    private SimpleDateFormat formatter = new SimpleDateFormat("E h:mm a");

    public EventsAdapter(Context context, int tvResId, List<Event> events){
        super(context, tvResId, events);
        this.events = events;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.event_row, null);
        }

        Event event = events.get(position);

        if (event != null) {
            TextView name = (TextView) v.findViewById(R.id.tvEventName);
            TextView description = (TextView) v.findViewById(R.id.tvEventDescription);
            TextView timeText = (TextView) v.findViewById(R.id.tvEventTimes);
            if (name != null) {
                name.setText(event.getName());
            }
            if (description != null) {
                description.setText(event.getDescription());
                if(event.getDescription().isEmpty()){
                    description.setVisibility(View.GONE);
                }
            }
            if (timeText != null) {
                StringBuilder timeString = new StringBuilder();
                if (event.isAllDay()){
                    timeString.append(new SimpleDateFormat("E").format(event.getEndDate()));
                    timeString.append(" All Day");
                } else {
                    long endTime = event.getEndDate().getTime();
                    timeString.append(formatter.format(event.getStartDate()));
                    if (endTime == 0) {
                        timeString.append(" onwards");
                    } else if (endTime != event.getStartDate().getTime()) {
                        timeString.append(" - ");
                        timeString.append(formatter.format(event.getEndDate()));
                    }
                }
                timeText.setText(timeString);
            }
        }

        if(event.getColor() != null && event.getColor() != "None"){
            v.setBackgroundColor(Color.parseColor(event.getColor()));
        }

        return v;
    }
}
