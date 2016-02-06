package com.alex.dacapo;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.dacapo.utils.AudioRecorder;
import com.example.alex.dacapo.R;

import java.io.File;

public class NewRecordingActivity extends AppCompatActivity {
    static final String LOG_TAG = NewRecordingActivity.class.getName();

    private AudioRecorder audioRecorder;
    private Chronometer recordTimer;
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

        // Holds user input for the title of the recording
        recordTitle = (EditText) findViewById(R.id.newRecordingTitle);

        // Timer for record length
        recordTimer = (Chronometer) findViewById(R.id.recordTimer);

        // Button used to toggle recording
        recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Button recordButton = (Button) view;
                recordStatus = (recordStatus + 1) % 2;
                if (recordStatus == 1) {
                    recordTimer.setBase(SystemClock.elapsedRealtime());
                    recordTimer.start();
                    // Recording begins
                    audioRecorder = new AudioRecorder();
                    audioRecorder.startRecording();
                    recordButton.setText("Stop Recording");
                } else {
                    audioRecorder.stopRecording();
                    recordTimer.stop();
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
                        File tempFile = new File(audioRecorder.getTempFilename());
                        if (tempFile.exists()) {
                            File newDir = new File(tempFile.getParent(), recordingTitle);
                            if (newDir.exists()) {
                                Toast.makeText(NewRecordingActivity.this, "Title is already being used", Toast.LENGTH_SHORT).show();
                            } else {
                                // Saving begins; renaming temp.3gp to match title
                                newDir.mkdir();
                                File newFile = new File(newDir, recordingTitle + ".pcm");
                                tempFile.renameTo(newFile);
                                Toast.makeText(NewRecordingActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                                // Go back to RecordingListActivity
                                Intent intent = new Intent();
                                intent.setClass(NewRecordingActivity.this, RecordingListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Log.e(LOG_TAG, "temp.pcm not found");
                        }
                    }
                }
            }
        });
    }

}
