package com.alex.dacapo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.dacapo.R;

import java.util.ArrayList;

/**
 * Created by alex on 1/5/16.
 */
public class RecordingListAdapter extends BaseAdapter {
    static final String LOG_TAG = RecordingListAdapter.class.getName();

    private Context context;
    private ArrayList<String> recordingTitles;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView recordingTitle;
    }

    public RecordingListAdapter(Context context, ArrayList<String> recordingTitles) {
        this.context = context;
        this.recordingTitles = recordingTitles;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_recording_list, null);
            holder = new ViewHolder();
            holder.recordingTitle = (TextView) convertView.findViewById(R.id.recordingTitle);
            holder.recordingTitle.setText(recordingTitles.get(position));
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
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
