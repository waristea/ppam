package com.example.william.activitylearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by William on 26/01/2018.
 */

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater; // to inflate a resource layout file to a view
    Alarm[] alarms;

    /*
    String[] items;
    String[] prices;
    String[] descriptions;

    public ItemAdapter(Context c,String[] i, String[] p, String[] d){
        items = i;
        prices = p;
        descriptions = d;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = items[position];
        String cost = prices[position];
        String desc = descriptions[position];

        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        TextView priceTextView = (TextView) v.findViewById(R.id.priceTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);

        nameTextView.setText(name);
        priceTextView.setText(cost);
        descriptionTextView.setText(desc);

        return v;
    }
    */
    public ItemAdapter(Context c, Alarm[] alarms){
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        Alarm alarm = alarms[position];

        String time = alarm.getTime();
        boolean isActive = alarm.isActive();
        String desc = alarm.getDescription();

        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView timeTextView = (TextView) v.findViewById(R.id.timeTextView);
        TextView isActiveTextView = (TextView) v.findViewById(R.id.activeTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);

        timeTextView.setText(time);
        isActiveTextView.setText("Active");
        descriptionTextView.setText(desc);

        return v;
    }

}
