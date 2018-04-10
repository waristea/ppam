package com.example.william.alarmy;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by William (WAW) on 03/03/2018.
 */

public class RingtonePlayingService extends Service {
    private boolean isRunning;
    private Context context;
    MediaPlayer mediaPlayer;
    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Log.d("RPS", "In the Richard service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Log.d("RPS", "start command initiated");

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        //Intent intent1 = new Intent(this.getApplicationContext(), ActiveAlarmActivity.class);
        //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        /*
        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Richard Dawkins is talking" + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
        */
        String state = intent.getExtras().getString("extra");

        Log.d("SERVICE", "TRIGGERED ON "+state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if(!this.isRunning && startId == 1) {
            Log.d("NOT-RINGING", "you want start");

            String ringtone = intent.getExtras().getString("RINGTONE");
            //String game = intent.getExtras().getString("GAME");
            int id = getResources().getIdentifier(ringtone, "raw", getPackageName());

            Log.d("NOW-RINGING : ", ringtone);
            //Log.d("NOW-RINGING : ", game);

            if (ringtone==null || id==0){
                id = R.raw.default_ringtone;
            }

            mediaPlayer = MediaPlayer.create(this, id);
            mediaPlayer.start();


            //mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.d("NOT-RINGING", "you want end");

            this.isRunning = false;
            this.startId = 0;

        }
        else if (this.isRunning && startId == 1){
            Log.d("RINGING", " you want to start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.d("RINGING", "you want to end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("RPS", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }
}
