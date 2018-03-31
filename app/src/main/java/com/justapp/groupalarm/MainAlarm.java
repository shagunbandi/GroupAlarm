package com.justapp.groupalarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.justapp.groupalarm.AlarmDBManager.COLUMN_HOUR;
import static com.justapp.groupalarm.AlarmDBManager.COLUMN_ID;
import static com.justapp.groupalarm.AlarmDBManager.COLUMN_INFO;
import static com.justapp.groupalarm.AlarmDBManager.COLUMN_LABEL;
import static com.justapp.groupalarm.AlarmDBManager.COLUMN_MIN;
import static com.justapp.groupalarm.AlarmDBManager.COLUMN_STATUS;

public class MainAlarm extends AppCompatActivity {

    // Imported
    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context;
    Calendar calendar;
    PendingIntent pendingIntent;

    // Local
    Boolean is_alarm_update;
    Boolean alarm_status_current;
    String labelName;
    AlarmDBManager dbManager;
    Alarms alarm;
    Intent alarm_receiver;

    // From the View
    TextView alarmStatus;
    Button saveButton;
    Button delButton;
    Button start_alarm;
    Button stop_alarm;
    EditText labelText;
    EditText infoText;
    int id;

    String TAG = "SHAGUN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default Stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alarm);

        //Initialize Class Variables
        initialize_variable();

        // Initialize this class Alarm and populate if update
        alarm = new Alarms();
        initialize_alarm();
        fill_main_activity();

        // Stop the Alarm, then Delete from database
        delButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i(TAG, "Del Button Clicked");

                        stop_alarm.performClick();
                        dbManager.delete_alarm(labelText.getText().toString());
                        back_to_main_alarm_intent();
                    }
                }
        );

        // Save to Alarm to DB
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        save_data_from_view_to_alarmDB();
                        back_to_main_alarm_intent();
                    }
                }
        );

        // Save the Alarm to DB then Start Alarm
        start_alarm.setOnClickListener(
                new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        Log.i(TAG, "start clicked");

                        // Get Hour and Min from timepicker
                        String hour = String.valueOf(timePicker.getHour());
                        String min = String.valueOf(timePicker.getMinute());

                        // Set this to Calender
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());

                        // Log the results
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:S");
                        String result = sdf.format(Calendar.getInstance().getTime());
                        Log.i(TAG, "Current:  " + result);
                        Log.i(TAG, "Play at:  " + hour + ":" + min);

                        // Update Status
                        set_alarm_status("Alarm is set at - " + hour + ":" + min);

                        // Pass info to alarm_reveiver
                        alarm_receiver.putExtra("extra", true);

                        // Save Alarm
                        save_data_from_view_to_alarmDB();

                        // Update te variable
                        is_alarm_update = true;

                        // Make Unique Alarm Receiver
                        alarm_receiver.setAction(String.valueOf(alarm.get_id()));
                        alarm_receiver.setData(Uri.parse(String.valueOf(alarm.get_id())));
                        pendingIntent = PendingIntent.getBroadcast(
                                MainAlarm.this,
                                0, alarm_receiver,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        alarm_status_current = true;

                    }
                }
        );

        stop_alarm.setOnClickListener(
                new View.OnClickListener() {

//                    TODO Older API Version ke liye dekh liyo
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "Stop clicked");

                        set_alarm_status("Alarm is Off");

//                        if(pendingIntent != null) {
//                            Log.i(TAG, "Pending Intent not null");
//                            alarmManager.cancel(pendingIntent);
//                            Log.i(TAG, "cancelling Pending Request");
//                            alarm_receiver.putExtra("extra", false);
//
//                            sendBroadcast(alarm_receiver);
//                            Log.i(TAG, "Broadcast Sent");
//                        }
                        
                        alarm_receiver.setAction(String.valueOf(alarm.get_id()));
                        alarm_receiver.setData(Uri.parse(String.valueOf(alarm.get_id())));
                        pendingIntent = PendingIntent.getBroadcast(
                                MainAlarm.this,
                                0, alarm_receiver,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                        alarmManager.cancel(pendingIntent);

                        alarm_status_current = false;

                        Intent stop_service = new Intent(MainAlarm.this, RingtonePlayingService.class);
                        stop_service.setAction(String.valueOf(alarm.get_id()));
                        stop_service.setData(Uri.parse(String.valueOf(alarm.get_id())));
                        stopService(stop_service);

                        stop_service = new Intent(MainAlarm.this, RingtonePlayingService.class);
                        stopService(stop_service);



                    }
                }
        );

    }

    private void back_to_main_alarm_intent() {
        Intent all_alarm_intent = new Intent(MainAlarm.this, ListActivity.class);
        MainAlarm.this.startActivity(all_alarm_intent);
    }

    private void initialize_variable() {

        //initialize variables
        this.context = this;
        timePicker = (TimePicker) findViewById(R.id.myTimePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        dbManager = new AlarmDBManager(this, null, null, 1);
        is_alarm_update = false;
        alarm_status_current = false;
        id = -1;
        alarm_receiver = new Intent(this.context, AlarmReceiver.class);

        alarmStatus = (TextView) findViewById(R.id.alarmStatus);
        saveButton = (Button) findViewById(R.id.saveButton);
        delButton = (Button) findViewById(R.id.delButton);
        start_alarm = (Button) findViewById(R.id.start_alarm);
        stop_alarm = (Button) findViewById(R.id.stop_alarm);
        labelText = (EditText) findViewById(R.id.labelText);
        infoText = (EditText) findViewById(R.id.infoText);

        Log.i(TAG, "Initialized all variables");

    }


    public void initialize_alarm() {
        Bundle listActivityData = getIntent().getExtras();
        if(listActivityData!=null) {
            labelName = listActivityData.getString("LabelName");
            HashMap<String, Object> map = dbManager.get_row_with_label(labelName);

            alarm.set_info((String) map.get(COLUMN_INFO));
            alarm.set_label((String) map.get(COLUMN_LABEL));
            alarm.set_id((int) map.get(COLUMN_ID));
            alarm.set_min((String) map.get(COLUMN_MIN));
            alarm.set_hour((String) map.get(COLUMN_HOUR));
            alarm.set_alarm_status((Boolean) map.get(COLUMN_STATUS));

            is_alarm_update = true;
        }

        Log.i(TAG, "Local Alarm Initialized");
    }

    private void fill_main_activity() {
        infoText.setText(alarm.get_info());
        labelText.setText(alarm.get_label());
        id = alarm.get_id();

        // Status
        boolean status = alarm.get_alarm_status();
        if(status) {
            alarmStatus.setText("Alarm is On !!");
            start_alarm.performClick();
        }
        else{
            alarmStatus.setText("Alarm is off !!");
            stop_alarm.performClick();
        }

        // timepicker
        String min = alarm.get_min();
        String hour = alarm.get_hour();

        if (!hour.equals("") && !min.equals("")){
            timePicker.setHour(Integer.parseInt(hour));
            timePicker.setMinute(Integer.parseInt(min));
        }

        Log.i(TAG, "Main Activity populated");
    }

    private void set_alarm_status(String status){
        alarmStatus.setText(status);
    }

    private void save_data_from_view_to_alarmDB(){
        alarm.set_info(infoText.getText().toString());
        alarm.set_label(labelText.getText().toString());
        alarm.set_hour(String.valueOf(timePicker.getHour()));
        alarm.set_min(String.valueOf(timePicker.getMinute()));
        alarm.set_alarm_status(alarm_status_current);

        // Update if already there else add new row
        if(MainAlarm.this.is_alarm_update) {
            dbManager.update_alarm(alarm, labelName);
            labelName = alarm.get_label();
        }
        else
            dbManager.add_alarm(alarm);

        Log.i(TAG, "Alarm Saved to DB");
    }
}
