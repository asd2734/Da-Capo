package com.alex.dacapo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by alex on 1/5/16.
 */
public class RecordingListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> recordingTitles;
    private LayoutInflater inflater;

    public RecordingListAdapter(Context context, ArrayList<String> recordingTitles) {
        this.context = context;
        this.recordingTitles = recordingTitles;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

    }

    @Override
    public int getCount() {
        return recordingTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
