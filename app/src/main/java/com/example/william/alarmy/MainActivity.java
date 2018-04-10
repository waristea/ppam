package com.example.william.alarmy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

// Cara kerja program:
//(. : belum diimplementasi ; ? : belum tahu caranya ; x : tidak jadi ; v : sudah)
//(v)1. List alarm pada sharedPreferences akan diload dengan menggunakan fungsi readAlarm
//      yang ada pada AlarmListing. Kemudian ditampilkan.
//(v)2. Pada saat user menekan tombol penambahan alarm, maka akan muncul halaman penambahan alarm.
//      Bila tombol 'OK' ditekan maka alarm akan disimpan, dan akan terpanggil onActivityResult.
//      Hal ini diatur pada tombol penambahan alarm.
//(v)3. onActivityResult akan membuat broadcast alarm sesuai dengan alarm yang di pass.
//(?)4. Bila waktunya telah tiba, AlarmReceiver akan menangkap broadcast tersebut dan
//      menggunakan RingtonePlayingService dan memanggil game yang akan dimainkan.
//      Bila game selesai dimainkan maka kan ditujukan pada halaman mematikan/snooze alarm


// To do list
// v 1. Nyalakan Toggle Alarm
// v 2. Buat ringtone untuk tiap alarm customizable
// 3. Buat detail alarm dgn listview

// Pertimbangan yang dapat dilakukan / tidak
// - Tidak men-save alarm di sharedPreferences


public class MainActivity extends AppCompatActivity {
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Shared Prefernces hanya ada di AlarmListing (read dan write) dan di Main untuk debugging
        // Debugging
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("SharedPref MAIN 0 ", sharedPref.getString("alarm_list", ""));

        // Read saved alarms
        AlarmListing alarmListing = new AlarmListing();
        ArrayList<Alarm> alarms = alarmListing.readAlarmList(getApplicationContext());
        alarmListing = new AlarmListing(alarms);

        setContentView(R.layout.activity_main);

        myListView = (ListView) findViewById(R.id.alarmListView);

        // Untuk list alarm
        ItemAdapter itemAdapter = new ItemAdapter(this, alarmListing.getAlarmArray());
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Intent showDetailActivity = new Intent(getApplicationContext(), DetailActivity.class);
                //showDetailActivity.putExtra("com.example.william.alarmy.ITEM_INDEX", position);
                startActivity(showDetailActivity);
                */
            }
        });

        // Untuk button add alarm
        FloatingActionButton addAlarmButton = (FloatingActionButton) findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(MainActivity.super.getApplication(), NewAlarmActivity.class);
                startActivityForResult(startIntent, Constant.NEW_ALARM_REQUEST_CODE);
                // Hasil di handle di onActivityResult
            }
        });

        // Debugging
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("Shared Pref MAIN", sharedPref.getString("alarm_list", ""));
    }

    // Menghandle hasil keluaran startActivityForResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (Constant.NEW_ALARM_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    Alarm alarm = data.getParcelableExtra("NEW_ALARM_REQUEST_CODE");
                    int id = java.lang.System.identityHashCode(alarm);
                    alarm.setId(id);

                    Log.d("TESTING-SAVE", alarm.getTime());
                    Log.d("TESTING-SAVE", alarm.getDescription());

                    // Insert into alarmlist
                    AlarmListing alarmListing = new AlarmListing();
                    alarmListing.addAlarmToSharedPref(alarm, this);

                    // Make new Alarm
                    alarmListing.scheduleAlarm(alarm, MainActivity.this);

                    // Refresh view
                    ItemAdapter itemAdapter = new ItemAdapter(this, alarmListing.getAlarmArray());
                    myListView.setAdapter(itemAdapter);
                }
                break;
            }
        }
    }
}
