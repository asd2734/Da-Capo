package com.alex.dacapo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.dacapo.R;

public class RecordingSelectionActivity extends AppCompatActivity {
    static final String LOG_TAG = RecordingSelectionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_selection);
    }
}
