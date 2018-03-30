package com.justapp.groupalarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainAlarm extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView alarmStatus;
    Context context;
    Calendar calendar;
    PendingIntent pendingIntent;
    String TAG = "SHAGUN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alarm);
        this.context = this;

        //initialize variables
        timePicker = (TimePicker) findViewById(R.id.myTimePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmStatus = (TextView) findViewById(R.id.alarmStatus);
        calendar = Calendar.getInstance();


        Button start_alarm = (Button) findViewById(R.id.start_alarm);
        Button stop_alarm = (Button) findViewById(R.id.stop_alarm);

        final Intent alarm_receiver = new Intent(this.context, AlarmReceiver.class);

        start_alarm.setOnClickListener(
                new View.OnClickListener() {


//                    TODO Older API Version ke liye dekh liyo
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "start clicked");
                        String hour = String.valueOf(timePicker.getHour());
                        String min = String.valueOf(timePicker.getMinute());

                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:S");
                        String result = sdf.format(Calendar.getInstance().getTime());


                        Log.i(TAG, "Current:  " + result);
                        Log.i(TAG, "Play at:  " + hour + ":" + min);

                        set_alarm_status("Alarm is set at - " + hour + ":" + min);

                        alarm_receiver.putExtra("extra", true);


                        //Create Intent till specified time
                        pendingIntent = PendingIntent.getBroadcast(
                                MainAlarm.this,
                                0, alarm_receiver,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);

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

                        if(pendingIntent != null) {
                            alarmManager.cancel(pendingIntent);
                            Log.i(TAG, "cancelling Pending Request");
                            alarm_receiver.putExtra("extra", false);

                            sendBroadcast(alarm_receiver);
                            Log.i(TAG, "Broadcast Sent");
                        }
                    }
                }
        );

    }

    private void set_alarm_status(String status){
        alarmStatus.setText(status);
    }
}
