package com.alex.dacapo.utils;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alex on 1/5/16.
 */
public class LocalStorage {
    static final String LOG_TAG = LocalStorage.class.getName();

    private static final String ROOTPATH = Environment.getExternalStorageDirectory() + "/DaCapo";

    public static String getRootPath() { return ROOTPATH; }

    public static ArrayList<String> listRecordings() {
        ArrayList<String> recordingList = new ArrayList<String>();
        File dir = new File(ROOTPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.length() != 0) {
            File[] files = dir.listFiles();
            for(File file : files){
                if(file.isDirectory()){
                    recordingList.add(file.getName());
                }
            }
        }

        Collections.sort(recordingList);
        return recordingList;
    }
}
