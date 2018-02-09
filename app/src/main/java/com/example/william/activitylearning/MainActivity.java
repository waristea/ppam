package com.example.william.activitylearning;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView myListView;
    String[] items;
    String[] prices;
    String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.alarmListView);
        items = res.getStringArray(R.array.items);
        prices = res.getStringArray(R.array.prices);
        descriptions = res.getStringArray(R.array.descriptions);

        // Layout files that will merge items and listview
        //myListView.setAdapter(new ArrayAdapter<String>(this, R.layout.my_listview_detail, items));

        // Use adapter to fill in informations on second activity
        /*
        ItemAdapter itemAdapter = new ItemAdapter(this, items, prices, descriptions);
        myListView.setAdapter(itemAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetailActivity = new Intent(getApplicationContext(), DetailActivity.class);
                showDetailActivity.putExtra("com.example.william.activitylearning.ITEM_INDEX", position);
                startActivity(showDetailActivity);

            }
        });
        */
        // testing

        Log.w("MARK ", "REACHED 0");
        int[] active={1,2,3};
        Alarm alarm = new Alarm(active, 7, 0);
        Alarm[] alarms = new Alarm[1];
        alarms[0] = alarm;

        ItemAdapter itemAdapter = new ItemAdapter(this, alarms);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Intent showDetailActivity = new Intent(getApplicationContext(), DetailActivity.class);
                //showDetailActivity.putExtra("com.example.william.activitylearning.ITEM_INDEX", position);
                startActivity(showDetailActivity);
                */
            }
        });
        Log.w("MARK ", "REACHED 1");



        // Add alarm button
        FloatingActionButton addAlarmButton = (FloatingActionButton) findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), NewAlarmActivity.class);
                startActivity(startIntent);
            }
        });

        Log.w("MARK ", "REACHED 2");
    }
}
