package com.winwang.clocks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wiwa on 8/8/15.
 */
public class CreateEventDialogFragment extends DialogFragment {
    final String TAG = "Event Creation Dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.creation_popup, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //builder.setView(dialogView);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;

    }

    @Override
    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        //dialog.getWindow().setLayout(width, height);
    }
}
