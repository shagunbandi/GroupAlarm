package com.justapp.groupalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    boolean stateId;
    boolean isRunning;
    final String TAG = "SHAGUN";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Ringtone Service Started");

        Boolean state = intent.getExtras().getBoolean("extra");

        Log.i(TAG, "stateId :" + this.stateId);

        if (state)
            this.stateId = true;
        else
            this.stateId = false;


        if(!this.isRunning && stateId){

            media_song = MediaPlayer.create(this, R.raw.sunny);
            media_song.start();
            Log.i(TAG, "No Music, and want on");
            this.isRunning = true;
            this.stateId = false;
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

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.i(TAG, "On Destroy Called");
        super.onDestroy();
        this.isRunning = false;

    }
}
