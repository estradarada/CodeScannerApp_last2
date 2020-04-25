package com.finastra.codescannerapp;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Items  {


    String objectName, worker08, worker15, alarm;

    public Items() {
    }


    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getWorker08() {
        return worker08;
    }

    public void setWorker08(String worker08) {
        this.worker08 = worker08;
    }

    public String getWorker15() {
        return worker15;
    }

    public void setWorker15(String worker15) {
        this.worker15 = worker15;
    }


    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}