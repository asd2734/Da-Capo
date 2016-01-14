package com.alex.dacapo;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.dacapo.utils.AudioRecord;
import com.alex.dacapo.utils.LocalStorage;
import com.example.alex.dacapo.R;

import java.io.File;

public class NewRecordingActivity extends AppCompatActivity {
    static final String LOG_TAG = NewRecordingActivity.class.getName();

    private AudioRecord audioRecord = new AudioRecord();
    private TextView recordTimer;
    private EditText recordTitle;
    private Button recordButton;
    private Button saveButton;

    private int recordStatus = 2;    // 2: hasn't recorded, 1: is recording, 0: recording stopped

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Timer for record length
        recordTimer = (TextView) findViewById(R.id.recordTimer);

        // Holds user input for the title of the recording
        recordTitle = (EditText) findViewById(R.id.newRecordingTitle);

        // Button used to toggle recording
        recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Button recordButton = (Button) view;
                recordStatus = (recordStatus + 1) % 2;
                if (recordStatus == 1) {
                    // Recording begins
                    audioRecord.startRecording();
                    recordButton.setText("Stop Recording");
                } else {
                    audioRecord.stopRecording();
                    recordButton.setText("Begin Recording");
                }
            }
        });

        // Button used to save recorded audio
        saveButton = (Button) findViewById(R.id.newRecordingSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (recordStatus == 2) {
                    Toast.makeText(NewRecordingActivity.this, "You haven't recorded yet!", Toast.LENGTH_SHORT).show();
                } else if (recordStatus == 1) {
                    Toast.makeText(NewRecordingActivity.this, "Recording in progress", Toast.LENGTH_SHORT).show();
                } else {
                    String recordingTitle = recordTitle.getText().toString().trim();
                    if (recordingTitle.equals("")) {
                        Toast.makeText(NewRecordingActivity.this, "Invalid title", Toast.LENGTH_SHORT).show();
                    } else {
                        // Must check existence of temp.3gp and availability of user-given title
                        File tempFile = new File(audioRecord.getTempFilename());
                        if (tempFile.exists()) {
                            File newDir = new File(tempFile.getParent(), recordingTitle);
                            if (newDir.exists()) {
                                Toast.makeText(NewRecordingActivity.this, "Title is already being used", Toast.LENGTH_SHORT).show();
                            } else {
                                // Saving begins; renaming temp.3gp to match title
                                newDir.mkdir();
                                File newFile = new File(newDir, recordingTitle + ".3gp");
                                tempFile.renameTo(newFile);
                                Toast.makeText(NewRecordingActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(LOG_TAG, "temp.3gp not found");
                        }
                    }
                }
            }
        });
    }

}
