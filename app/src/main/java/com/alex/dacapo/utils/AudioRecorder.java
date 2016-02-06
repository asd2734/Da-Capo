package com.alex.dacapo.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alex on 2/6/16.
 */
public class AudioRecorder {
    static final String LOG_TAG = AudioRecorder.class.getName();

    private AudioRecord recorder = null;
    private static final int samplingRate = 44100;  // 44100Hz sampling
    private Thread recordingThread;
    private boolean isRecording = false;
    private String filename = LocalStorage.getRootPath() + "/temp.pcm"; // temp.pcm will be renamed once the user has finished recording

    public void startRecording() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, samplingRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 2048);
        isRecording = true;
        recorder.startRecording();

        recordingThread = new Thread(new Runnable() {
            public void run() {
                short micBuffer[] = new short[1024];

                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(filename);
                    while (isRecording) {
                        recorder.read(micBuffer, 0, 1024);
                        try {
                            byte byteBuffer[] = short2byte(micBuffer);
                            os.write(byteBuffer, 0, 1024 * 2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private byte[] short2byte(short[] shortBuffer) {
                byte[] bytes = new byte[shortBuffer.length * 2];
                for (int i = 0; i < shortBuffer.length; i++) {
                    bytes[i * 2] = (byte) (shortBuffer[i] & 0x00FF);
                    bytes[(i * 2) + 1] = (byte) (shortBuffer[i] >> 8);
                    shortBuffer[i] = 0;
                }
                return bytes;

            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    public void stopRecording() {
        isRecording = false;
        recorder.stop();
        recorder.release();
        recorder = null;
        recordingThread = null;
    }

    public String getTempFilename() {
        return filename;
    }
}
