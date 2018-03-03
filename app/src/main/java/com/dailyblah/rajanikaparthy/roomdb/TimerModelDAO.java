package com.dailyblah.rajanikaparthy.roomdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

@Dao
@TypeConverters(DateConverter.class)

public interface TimerModelDAO {

    @Query("select * from TimerModel")
    LiveData<List<TimerModel>> getAllTimeredItems();

    @Query("select * from TimerModel where id = :id")
    TimerModel getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addTimer(TimerModel timerModel);

    @Delete
    void deleteTimer(TimerModel timerModel);
}
