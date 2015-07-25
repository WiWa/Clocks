package com.winwang.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arbiter on 7/25/2015.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    private List<Event> events;

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
            TextView details = (TextView) v.findViewById(R.id.tvEventDetails);
            if (name != null) {
                name.setText(event.getName());
            }
            if (description != null) {
                description.setText(event.getDescription());
            }
            if (details != null) {
                details.setText(event.toString());
            }
        }

        return v;
    }
}
