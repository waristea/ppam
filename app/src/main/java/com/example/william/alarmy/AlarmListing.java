package com.example.william.alarmy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by William (WAW) on 01/03/2018.
 */

class AlarmListing {
    ArrayList<Alarm> alarms;

    AlarmListing(){
    }

    AlarmListing(ArrayList<Alarm> alarms){
        this.alarms = alarms;
    }

    // Read alarmList from sharedPreferences
    ArrayList<Alarm> readAlarmList(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Alarm> alarms_temp = new ArrayList<>();

        if(sharedPref.contains("initialized")) {
            Log.d("MARKING ", "ALARM SHARED PREF READ");

            Gson gson = new Gson();
            String alarm_list_json = sharedPref.getString("alarm_list", "");

            Type listType = new TypeToken<ArrayList<Alarm>>() {}.getType();

            if (!alarm_list_json.equals("")){
                alarms_temp = gson.fromJson(alarm_list_json, listType);
            }
            else{
                Alarm alarm = new Alarm();
                alarms_temp.add(alarm);
            }
        }
        else{
            Log.d("MARKING ", "ALARM SHARED PREF NOT FOUND, WRITING...");

            Alarm alarm = new Alarm();
            alarms_temp.add(alarm);

            SharedPreferences.Editor ed = sharedPref.edit();

            //Indicate that the default shared prefs have been set
            ed.putBoolean("initialized", true);

            //Set some default shared pref
            Gson gson = new Gson();
            String json = gson.toJson(alarms_temp);

            ed.putString("alarm_list", json);
            ed.apply();
        }

        return alarms_temp;
    }

    // Write alarmList to sharedPreferences
    void setAlarmList(ArrayList<Alarm> alarmList, Context c){
        this.alarms = alarmList;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);;
        SharedPreferences.Editor ed = sharedPref.edit();

        //Indicate that the default shared prefs have been set
        ed.putBoolean("initialized", true);

        //Set some default shared pref
        Gson gson = new Gson();
        String json = gson.toJson(this.alarms);

        ed.putString("alarm_list", json);
        ed.apply();
    }

    Alarm[] getAlarmArray(){
        return this.alarms.toArray(new Alarm[alarms.size()]);
    }

    public void scheduleAlarm(Alarm alarm, Context c){
        Intent alarmIntent = new Intent(c, AlarmReceiver.class);
        alarmIntent.putExtra("ALARM_ID", alarm.getId());

        Log.d("MAKE_RING0", "Hereby is the Alarm sent with message : "+ alarm.getId());

        AlarmManager alarmManager = (AlarmManager) c.getSystemService(MainActivity.ALARM_SERVICE);

        int i = 0;
        for(Calendar calendar : alarm.getCalendarList()){
            try {
                Log.d("SCHEDULE-TEST", calendar.toString());
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(c, alarm.getId()*10 + i, alarmIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar currentCalendar = Calendar.getInstance();

                if(calendar.getTimeInMillis()<currentCalendar.getTimeInMillis()){
                    calendar.add(Calendar.DATE, 7);
                }

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            catch (Exception e){
                Log.e("ALARM-ERROR", e.getMessage());
            }
            ++i;
        }
        Log.d("SCHEDULE-TEST", "Schedule alarm reached");
    }

    public void cancelAlarm(Alarm alarm, Context c){
        Intent alarmIntent = new Intent(c, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(MainActivity.ALARM_SERVICE);

        for(int i=0; i<alarm.getCalendarList().size(); i++){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(c,
                    (alarm.getId()*10)+i, alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);

            ++i;
        }
    }

    public Alarm findAlarm(Context c, int id){
        ArrayList<Alarm> alarmArrayList = new ArrayList<>(readAlarmList(c));

        Alarm targetAlarm = null;
        for(Alarm a : alarmArrayList){
            if(a.getId()==id){
                targetAlarm = a;
            }
        }

        return targetAlarm;
    }

    public void scheduleAllAlarms(Context c){
        Intent alarmIntent = new Intent(c, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(MainActivity.ALARM_SERVICE);

        for(Alarm alarm : alarms){
            scheduleAlarm(alarm, c);
        }
    }

    public void cancelAllAlarms(Context c){
        Intent alarmIntent = new Intent(c, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(MainActivity.ALARM_SERVICE);

        for(Alarm alarm : alarms){
            cancelAlarm(alarm, c);
        }
    }

    // Read alarm from alarmList from sharePref and save to it
    public void addAlarmToSharedPref(Alarm alarm, Context c){
        ArrayList<Alarm> alarmArrayList = new ArrayList<>(readAlarmList(c));
        alarmArrayList.add(alarm);
        setAlarmList(alarmArrayList, c);
        alarms = alarmArrayList;
    }

    // Public renew a certain alarm (not handling cancel and reschedule alarm), returns old alarm
    public Alarm updateAlarm(Alarm alarm, Context c){
        ArrayList<Alarm> alarmArrayList = new ArrayList<>(readAlarmList(c));

        Alarm targetAlarm = null;
        for(Alarm a : alarmArrayList){
            if(a.getId()==alarm.getId()){
                targetAlarm = a;
            }
        }

        if (targetAlarm!=null){
            alarmArrayList.remove(targetAlarm);
            alarmArrayList.add(alarm);

            setAlarmList(alarmArrayList, c);
            alarms = alarmArrayList;
        }

        return targetAlarm;
    }
}
