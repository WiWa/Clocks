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
    private SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

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
            TextView startDate = (TextView) v.findViewById(R.id.tvEventStart);
            TextView endDate = (TextView) v.findViewById(R.id.tvEventEnd);
            if (name != null) {
                name.setText(event.getName());
            }
            if (description != null) {
                description.setText(event.getDescription());
                if(event.getDescription().isEmpty()){
                    description.setVisibility(View.GONE);
                }
            }
            if (startDate != null) {
                startDate.setText("From: "+formatter.format(event.getStartDate()));
            }
            if (endDate != null) {
                if(event.getEndDate().getTime() == 0){
                    endDate.setVisibility(View.GONE);
                }
                else {
                    endDate.setText("Until: " + formatter.format(event.getEndDate()));
                }
            }
        }

        if(event.getColor() != null && event.getColor() != "None"){
            v.setBackgroundColor(Color.parseColor(event.getColor()));
        }

        return v;
    }
}
