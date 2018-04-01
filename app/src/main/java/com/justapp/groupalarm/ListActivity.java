package com.justapp.groupalarm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends Activity {

    final String TAG = "SHAGUN";
    AlarmDBManager dbManager;
    Button addButton;
    Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize Buttons
        addButton = (Button) findViewById(R.id.addButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        dbManager = new AlarmDBManager(this, null, null, 1);

        //Set List Values
        String[] labelArr = dbManager.get_all_labels();
        ListAdapter myAdapter = new CustomAdapter(this, labelArr);
        ListView myList = (ListView) findViewById(R.id.myList);
        myList.setAdapter(myAdapter);

        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent new_alarm_intent = new Intent(ListActivity.this, MainAlarm.class);
                        ListActivity.this.startActivity(new_alarm_intent);
                    }
                }
        );
        stopButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopService(new Intent(getBaseContext(), RingtonePlayingService.class));
                    }
                }
        );

        myList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String alarm_label = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(ListActivity.this, alarm_label, Toast.LENGTH_LONG).show();
//                        TODO Switch to Main Alarm

                        Intent alarm_detail_intent = new Intent(ListActivity.this, MainAlarm.class);
                        alarm_detail_intent.putExtra("LabelName", alarm_label);
                        ListActivity.this.startActivity(alarm_detail_intent);

                    }
                }
        );
    }


}
