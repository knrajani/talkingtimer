package com.dailyblah.rajanikaparthy.talkingtimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rajanikaparthy on 2018-02-09.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] stringNames;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] stringNames) {
        this.context = applicationContext;
        this.stringNames = stringNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return stringNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(stringNames[i]);
        return view;
    }
}