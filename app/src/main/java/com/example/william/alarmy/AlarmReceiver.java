package com.example.william.alarmy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by William (WAW) on 02/03/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int message = intent.getIntExtra("ALARM_ID", 0);
        Log.d("RING0", "Hereby is the AlarmReceiver with message : " + message);

        AlarmListing alarmListing = new AlarmListing();
        Alarm alarm = alarmListing.findAlarm(context, message);

        Intent startIntent = new Intent(context, ActiveAlarmActivity.class);
        startIntent.putExtra("RINGTONE", alarm.getRingtone());
        startIntent.putExtra("GAME", alarm.getGame());
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(startIntent);

    }
}
