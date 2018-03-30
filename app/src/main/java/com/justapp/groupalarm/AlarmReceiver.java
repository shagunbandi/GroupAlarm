package com.justapp.groupalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("SHAGUN", "In the Receiver");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        context.startService(service_intent);

    }
}
