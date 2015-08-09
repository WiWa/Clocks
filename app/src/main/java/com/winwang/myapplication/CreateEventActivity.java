package com.winwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Arbiter on 8/9/2015.
 */
public class CreateEventActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_popup);

        final EditText eventName= (EditText) findViewById(R.id.etCreateEventName);

        final Button btnCreateEvent = (Button) findViewById(R.id.btnCreateEventConfirm);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name = eventName.getText().toString();
                if(event_name.length() == 0 || event_name == null){
                    event_name = "No Nam";
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("event_name", event_name);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
