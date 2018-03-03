package com.dailyblah.rajanikaparthy.listitem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.dailyblah.rajanikaparthy.roomdb.AppDatabase;
import com.dailyblah.rajanikaparthy.roomdb.TimerModel;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

public class AddTimerViewModel extends AndroidViewModel{

    private AppDatabase appDatabase;

    public AddTimerViewModel(@NonNull final Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addTimer(final TimerModel timerModel) {
        new addAsyncTask(appDatabase).execute(timerModel);
    }

    private static class addAsyncTask extends AsyncTask<TimerModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final TimerModel... params) {
            db.itemAndPersonModel().addTimer(params[0]);
            return null;
        }

    }
}
