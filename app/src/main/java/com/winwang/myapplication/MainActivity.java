package com.winwang.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.Vector;


public class MainActivity extends ActionBarActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       initEventsList();

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

        return super.onOptionsItemSelected(item);
    }

    public void initEventsList(){
        ListView list = (ListView) findViewById(R.id.EventsList);
        list.setClickable(true);

        final List<Event> eventsList= new Vector<Event>();
        eventsList.add(new Event());
        eventsList.add(new Event("Test1", "1234455"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));
        eventsList.add(new Event("Test2", "00000"));

        EventsAdapter adapter = new EventsAdapter(this, R.id.EventsList, eventsList);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                System.out.println("sadsfsf");
                showToast(eventsList.get(position).getName() + ", " + String.valueOf(index));
            }
        });

        list.setAdapter(adapter);
    }

    public void initCreateButton(){
        ((Button) findViewById(R.id.btnCreateEvent)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "Creation Button!! :  btnCreateEvent");
            }
        });
    }
}
