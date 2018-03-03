package com.dailyblah.rajanikaparthy.roomdb;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    //long milisecond to hr:mm:ss
    public String StringToTime(String str){

        int p1=0,p2=0,p3=0,seconds = 0;
        try
        {
            seconds = Integer.parseInt(str);
            p1 = seconds % 60;
            p2 = seconds / 60;
            p3 = p2 % 60;
            p2 = p2 / 60;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return (p1 + ":"+p2 + ":" + p3);
    }

    //string hr:mm:ss to long milisecond
    public String TimeToString(String str){
        long result = 0;
        try{
            String td[] = str.split(":");
            result = Integer.parseInt(td[0]) * 1000 + Integer.parseInt(td[1]) * 60 * 1000 + Integer.parseInt(td[2]) * 60 * 60 * 1000;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

}
