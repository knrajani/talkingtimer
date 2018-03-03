package com.dailyblah.rajanikaparthy.roomdb;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by rajanikaparthy on 2018-03-01.
 */

@Entity
public class TimerModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String timerString;
    @TypeConverters(DateConverter.class)
    String longString;

    String timerTime;

    public TimerModel(final String timerString, final String timerTime) {
        this.timerString = timerString;
        this.timerTime = timerTime;
    }

    public String getTimerId(){ return String.valueOf(id);}

    public String getTimerString() {
        return timerString;
    }

    public void setTimerString(final String timerString) {
        this.timerString = timerString;
    }

    public String getTimerTime() {
        return timerTime;
    }

    public void setTimerTime(final String timerTime) {
        this.timerTime = timerTime;
    }

    public  String TimeToString(){
        return longString;
    }
}

