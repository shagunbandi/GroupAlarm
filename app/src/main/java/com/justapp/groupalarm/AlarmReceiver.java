package com.justapp.groupalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shagunbandi on 30/03/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    final String TAG = "SHAGUN";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "In the Receiver");

        Boolean get_extra = intent.getExtras().getBoolean("extra");
        String label = intent.getExtras().getString("label");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        service_intent.putExtra("extra", get_extra);
        service_intent.putExtra("label", label);
        context.startService(service_intent);

    }
}
