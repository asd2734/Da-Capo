package com.alex.dacapo.utils;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by alex on 1/14/2016.
 */
public class AudioRecord {
    static final String LOG_TAG = AudioRecord.class.getName();

    private MediaRecorder recorder = null;

    private String filename = LocalStorage.getRootPath() + "/temp.3gp"; // temp.3gp will be renamed once the user has finished recording

    public void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filename);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public String getTempFilename() {
        return filename;
    }
}
