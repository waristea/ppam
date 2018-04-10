package com.example.william.alarmy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class NewAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker);
        timepicker.setIs24HourView(true);

        // Tombol OK
        Button okAlarmButtton = (Button) findViewById(R.id.ok_alarm_button);
        okAlarmButtton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Alarm alarm = saveAlarm();

                Intent resultIntent = new Intent();

                // Ditangkap oleh onActivityResult di MainActivity
                resultIntent.putExtra("NEW_ALARM_REQUEST_CODE", alarm);
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            }
        });

        // Tombol Cancel
        Button cancelAlarmButton = (Button) findViewById(R.id.cancelButton);
        cancelAlarmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    // Dijalankan saat tombol OK ditekan
    protected Alarm saveAlarm(){
        Alarm alarm;
        int[] activeDays = new int[7];

        TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker);

        int hour = timepicker.getCurrentHour();
        int minute = timepicker.getCurrentMinute();


        for(int i = 0; i<Constant.daysLower.length; i++){

            int toggleId = getResources().getIdentifier(Constant.daysLower[i]+"Toggle", "id", getPackageName());
            ToggleButton toggleButton = (ToggleButton) findViewById(toggleId);

            // Bagian ini dioptimasi jika mau (Kenapa gw tulis ini?)
            if (toggleButton.isActivated()){
                activeDays[i] = i+1;
                //Log.d("ACTIVE-DAYS-A", Arrays.toString(activeDays));
            }
            if (toggleButton.isChecked()){
                activeDays[i] = i+1;
                //Log.d("ACTIVE-DAYS-B", Arrays.toString(activeDays));
            }
        }

        //Log.d("ACTIVE-DAYS", Arrays.toString(activeDays));

        alarm = new Alarm(activeDays, hour,minute);

        return alarm;
    }

    /*
    protected void saveAlarm(){
        Alarm alarm;
        int[] activeDays = new int[7];

        TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker);

        int hour = timepicker.getCurrentHour();
        int minute = timepicker.getCurrentMinute();


        for(int i = 0; i<Constant.daysLower.length; i++){

            int toggleId = getResources().getIdentifier(Constant.daysLower[i]+"Toggle", "id", getPackageName());
            ToggleButton toggleButton = (ToggleButton) findViewById(toggleId);

            if (toggleButton.isActivated()){
                activeDays[i] = i+1;
                Log.d("ACTIVE-DAYS-A", Arrays.toString(activeDays));
            }
            if (toggleButton.isChecked()){
                activeDays[i] = i+1;
                Log.d("ACTIVE-DAYS-B", Arrays.toString(activeDays));
            }
        }

        Log.d("ACTIVE-DAYS", Arrays.toString(activeDays));

        alarm = new Alarm(activeDays, hour,minute);

        AlarmListing alarmListing = new AlarmListing();
        ArrayList<Alarm> alarmArrayList = new ArrayList<>(alarmListing.readAlarmList(this));
        alarmArrayList.add(alarm);
        alarmListing.setAlarmList(alarmArrayList,this);

    }
    */
}
