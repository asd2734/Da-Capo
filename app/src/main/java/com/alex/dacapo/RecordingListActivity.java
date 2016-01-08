package com.alex.dacapo;

import android.content.Intent;
import android.net.LocalSocket;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alex.dacapo.utils.LocalStorage;
import com.alex.dacapo.utils.RecordingListAdapter;
import com.example.alex.dacapo.R;

import java.io.File;
import java.util.ArrayList;

public class RecordingListActivity extends AppCompatActivity {

    static ListView recordingListView;
    static ArrayList<String> recordingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recordingListView = (ListView) findViewById(R.id.recordingList);
        recordingListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(RecordingListActivity.this, RecordingSelectionActivity.class);
                intent.putExtra("selection", recordingList.get(position));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        File recordingDir = new File(LocalStorage.recordDir());
        if(!recordingDir.exists()){
            recordingDir.mkdirs();
        }

        updateRecordingList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RecordingListActivity.this, NewRecordingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recording_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateRecordingList() {
        recordingList = LocalStorage.listRecordings();
        recordingListView.setAdapter(new RecordingListAdapter(this, recordingList));
    }
}
