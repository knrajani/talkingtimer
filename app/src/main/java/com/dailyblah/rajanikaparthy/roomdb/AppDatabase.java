package com.dailyblah.rajanikaparthy.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

@Database(entities = {TimerModel.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timer_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract TimerModelDAO itemAndPersonModel();
}
