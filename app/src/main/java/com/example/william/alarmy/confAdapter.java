package com.example.william.alarmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by William (WAW) on 09/04/2018.
 */
public class confAdapter extends BaseAdapter {
    LayoutInflater mInflater; // to inflate a resource layout file to a view
    Object[] o;
    Context c;

    @Override
    public int getCount() {
        return o.length;
    }

    @Override
    public Object getItem(int position) {
        return o[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);


        return v;
    }
}
