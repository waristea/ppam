package com.example.william.alarmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by William on 26/01/2018.
 */

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater; // to inflate a resource layout file to a view
    Alarm[] alarms;
    Context c;

    public ItemAdapter(Context c, Alarm[] alarms){
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        return alarms.length;
    }

    @Override
    public Object getItem(int position) {
        return alarms[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Alarm alarm = alarms[position];

        String time = alarm.getTime();
        boolean isActive = alarm.isActive();
        String desc = alarm.getDescription();

        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView timeTextView = (TextView) v.findViewById(R.id.timeTextView);
        final Switch alarmActiveToggle = (Switch) v.findViewById(R.id.alarmActiveToggle);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);

        timeTextView.setText(time);
        alarmActiveToggle.setChecked(isActive);
        descriptionTextView.setText(desc);

        // Kalau ngebug, cari errornya disini, kemungkinan nya tinggi
        alarmActiveToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!alarm.isActive() && isChecked){
                    AlarmListing alarmListing = new AlarmListing();
                    alarm.setActive(true);

                    alarmListing.updateAlarm(alarm, c);
                    alarmListing.scheduleAlarm(alarm, c);

                    buttonView.setChecked(false);
                }
                else{
                    if(alarm.isActive() && !isChecked){
                        AlarmListing alarmListing = new AlarmListing();
                        alarm.setActive(false);

                        Alarm oldAlarm = alarmListing.updateAlarm(alarm, c);
                        alarmListing.cancelAlarm(oldAlarm, c);

                        buttonView.setChecked(true);
                    }
                }
            }
        });

        return v;
    }
}
