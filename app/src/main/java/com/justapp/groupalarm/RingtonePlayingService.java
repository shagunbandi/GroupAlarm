package com.justapp.groupalarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class RingtonePlayingService extends Service {
    MediaPlayer media_song;
    boolean stateId;
    boolean isRunning = false;
    final String TAG = "SHAGUN";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Ringtone Service Started");
        this.stateId = intent.getExtras().getBoolean("extra");
        Log.i(TAG, "stateId :" + this.stateId);
        Log.i(TAG, "Alarm with Label "+ intent.getExtras().getString("label") + " is starting");

        // Checks if music is Running or Not and what is Requested and acts accordingly
        set_media_on_off();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.i(TAG, "On Destroy Called");
        super.onDestroy();
        media_song.stop();
        media_song.reset();
        this.isRunning = false;

    }

    public void set_media_on_off(){
        if(!this.isRunning && stateId){

            media_song = MediaPlayer.create(this, R.raw.sunny);
            media_song.start();
            media_song.setLooping(true);
            Log.i(TAG, "No Music, and want on");
            this.isRunning = true;
            this.stateId = false;

            notification_on();

        }
        else if (this.isRunning && !stateId){
            Log.i(TAG, "Music, and want off");
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.stateId = false;
        }
        else if (!this.isRunning && !stateId){
            Log.i(TAG, "No Music, and want off");
            this.isRunning = false;
            this.stateId = false;

        }
        else if (this.isRunning && stateId){
            Log.i(TAG, "Music, and want on");

            this.isRunning = true;
            this.stateId = true;

        }

        Log.i(TAG, "Request Complete");
    }

    private void notification_on() {

        //Notifications
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainAlarm.class);

        PendingIntent pendingIntent_main_activity = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

        Notification notification_pop = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification_pop = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setTicker("This is the ticker")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("Here is the Title")
                    .setContentText("Here is some Text")
                    .build();
        }


        assert notificationManager != null;
        notificationManager.notify(0,notification_pop);

    }
}
