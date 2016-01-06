package com.example.alex.dacapo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alex on 1/5/16.
 */
public class LocalStorage {
    private static final String ROOTPATH = "/sdcard/DaCapo";
    private static final String RECORDPATH = ROOTPATH + "/recordings";
    private static final String SHEETPATH = ROOTPATH + "/sheets";

    public static String recordDir() {
        return RECORDPATH;
    }

    public static String sheetDir() {
        return SHEETPATH;
    }

    public static ArrayList<String> listRecordings() {
        ArrayList<String> recordingList = new ArrayList<String>();
        File dir = new File(RECORDPATH);
        if(dir.length() != 0){
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
