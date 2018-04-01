package com.justapp.groupalarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * Created by shagunbandi on 28/03/18.
 */

//class CustomAdapter extends ArrayAdapter<String>{
//
//    public CustomAdapter(@NonNull Context context, String[] foods) {
//        super(context, R.layout.cutom_row, foods);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater myInflator = LayoutInflater.from(getContext());
//        View customView = myInflator.inflate(R.layout.cutom_row, parent, false);
//
//        String singleFoodItem = getItem(position);
//        TextView myText = (TextView) customView.findViewById(R.id.TVAlarm);
//
//        myText.setText(singleFoodItem);
//        return customView;
//    }
//}

class CustomAdapter extends ArrayAdapter<Alarms>{

    public CustomAdapter(@NonNull Context context, Alarms[] all_alarms) {
        super(context, R.layout.cutom_row, all_alarms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.cutom_row, parent, false);

        Alarms singleAlarm = getItem(position);
        TextView alarmLabel = (TextView) customView.findViewById(R.id.TVAlarm);
        TextView alarmStatus = (TextView) customView.findViewById(R.id.TVStatus);
        TextView alarmTime = (TextView) customView.findViewById(R.id.TVTime);

        // Set Label
        assert singleAlarm != null;
        alarmLabel.setText(singleAlarm.get_label());

        // Set Time
        alarmTime.setText(singleAlarm.get_time_AMPM());

        // Set Status
        alarmStatus.setText(singleAlarm.get_OnOff());
        return customView;
    }
}
