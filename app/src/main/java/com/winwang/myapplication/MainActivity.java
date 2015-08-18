package com.winwang.myapplication;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class MainActivity extends ActionBarActivity {

    String TAG = "MainActivity";
    final List<Event> eventsList= new Vector<Event>();
    ListView mListView;
    EventsAdapter mEventsAdapter;
    DonutsVisualization mDonuts;

    static final int CREATE_NEW_EVENT = 0;
    static final int GOOGLE_CALENDAR_EVENTS = 1;
    static final long MS_IN_A_DAY = 86400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEventsList();
        initClocks();
        initCreateButton();

        Log.d(TAG, "Main Activity Created :3");
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
        if (id == R.id.action_myitem) {
            Intent googleCalendarIntent = new Intent(MainActivity.this, GoogleCalendarQuickStart.class);
            startActivityForResult(googleCalendarIntent, GOOGLE_CALENDAR_EVENTS);
        }

        return super.onOptionsItemSelected(item);
    }

    public void initClocks(){
/*
        ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParhow to pass
        lp.width = 200;
        lp.height = 400
        mDonuts = new DonutsVisualization(MainActivity.this, )
*/
        mDonuts = (DonutsVisualization) findViewById(R.id.dvClocks);
    }

    public void initEventsList(){
        mListView = (ListView) findViewById(R.id.EventsList);
        mListView.setClickable(true);


        eventsList.add(new Event());
        /*
        eventsList.add(new Event("Test1", "1234455"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test3", "1234567890"));
        */

        mEventsAdapter = new EventsAdapter(this, R.id.EventsList, eventsList);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                System.out.println("sadsfsf");
                showToast(eventsList.get(position).getName() + ", " + String.valueOf(index));
            }
        });

        mListView.setAdapter(mEventsAdapter);
    }

    public int pxToDp(int pix){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pix, getResources().getDisplayMetrics());
    }

    public void initCreateButton(){
        final Button btnCreateEvent, btnCreateConfirm, btnCreateCancel;
        final LinearLayout popupLayout;


        btnCreateEvent = (Button) findViewById(R.id.btnCreateEvent);
  //      btnCreateConfirm = (Button) popupView.findViewById(R.id.btnCreateConfirm);
  //      btnCreateCancel = (Button) popupView.findViewById(R.id.btnCreateCancel);


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Creation Button!! :  btnCreateEvent");

                Intent createEventIntent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivityForResult(createEventIntent, CREATE_NEW_EVENT);
                //popupCreateEvent.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                // Use the Builder class for convenient dialog construction
/*super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                LinearLayout dialogLayout = (LinearLayout) findViewById(R.id.linlayCreate);
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.creation_popup, dialogLayout, true);

                final EditText eventName = (EditText) dialogView.findViewById(R.id.etCreateEventName);
                final EditText eventDescription = (EditText) dialogView.findViewById(R.id.etCreateEventDescription);
//                final DatePicker eventDate = (DatePicker) dialogView.findViewById(R.id.CreateEventDatePicker);
//                final TimePicker eventTime = (TimePicker) dialogView.findViewById(R.id.CreateEventTimePicker);
                eventName.setText("Sample Name");
                */

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("New Event")
                .setMessage("Name and Date are required fields.")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Create Confirm, Name: " + eventName.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Create Cancel");
                        dialog.cancel();
                    }
                });
                //dialog.getWindow().setLayout(800, 1400);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = pxToDp(800);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialog.show();

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                */
                /*
                FragmentManager fm = getFragmentManager();
                CreateEventDialogFragment frag = new CreateEventDialogFragment();
                //frag.show(ft, "txn_tag");
                FragmentTransaction ft = fm.beginTransaction();
                // For a little polish, specify a transition animation
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                // To make it fullscreen, use the 'content' root view as the container
                // for the fragment, which is always the root view for the activity
                ft.add(android.R.id.content, frag)
                        .addToBackStack(null).commit();
                */


            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CREATE_NEW_EVENT) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                String event_name = data.getExtras().getString("event_name");
                Log.d(TAG, "Success!! :  " + event_name);
                //Event new_event = new Event(event_name, "Returned from creation");
                //eventsList.add(0,new_event);
            }
        }
        if (requestCode == GOOGLE_CALENDAR_EVENTS) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                //String event_name = data.getExtras().getString("events");
                ArrayList<String> colorIDs = data.getExtras().getStringArrayList("colorIDs");
                ArrayList<String> colors = data.getExtras().getStringArrayList("colors");
                Parcelable[] uncastevents = data.getExtras().getParcelableArray("events");

                Log.d(TAG, "Google Events Received:  " + uncastevents);

                for(Parcelable uncastEvent : uncastevents){
                    eventParcelable castEvent = (eventParcelable) uncastEvent;
                    int colorIndex = colorIDs.indexOf(castEvent.getmColorID());
                    Event event = new Event(castEvent);

                    String eventColor = "None";
                    if(colorIndex >= 0){
                        eventColor = colors.get(colorIndex);
                        event.setColor(eventColor);
                    }
                    Log.d(TAG, "get mColorID: " + castEvent.getmColorID());
                    Log.d(TAG, "colorIndex: " + colorIndex);
                    Log.d(TAG, "eventColor: " + eventColor);

                    Log.d(TAG, "GOOGLE EVENT DATA: " + event.toString());
                    //eventsList.add(0, event);
                    eventsList.add(0, event);
                    mDonuts.addArc(arcFromEvent(event));
                }

                String colorIDstring = "";
                for (String colorID : colorIDs){
                    colorIDstring += colorID + ", ";
                }
                Log.d(TAG, "COLORIDS:" + colorIDstring);

                String colorstring = "";
                for (String color : colors){
                    colorstring += color + ", ";
                }
                Log.d(TAG, "COLORS:" + colorstring);

                Log.d(TAG, "ColorsArrayList: " + colors);

                mEventsAdapter.notifyDataSetChanged();

            }
        }
    }

    private DonutsArc arcFromEvent(Event event){
        double msStart = event.msSinceMidnight(event.getStartDate());
        double msEnd = event.msSinceMidnight(event.getEndDate());

        double startDegree = (msStart / MS_IN_A_DAY) * 360;
        double endDegree = (msEnd / MS_IN_A_DAY) * 360;

        return new DonutsArc(mDonuts, startDegree, endDegree,
                Color.GRAY, Color.parseColor(event.getColor()));
    }


}
