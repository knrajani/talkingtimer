package com.dailyblah.rajanikaparthy.listitem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.dailyblah.rajanikaparthy.roomdb.AppDatabase;
import com.dailyblah.rajanikaparthy.roomdb.TimerModel;

import java.util.List;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

public class TimerListViewModel extends AndroidViewModel {


    private final LiveData<List<TimerModel>> itemAndPersonList;

    private AppDatabase appDatabase;

    public TimerListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndPersonList = appDatabase.itemAndPersonModel().getAllTimeredItems();
    }


    public LiveData<List<TimerModel>> getItemAndPersonList() {
        return itemAndPersonList;
    }

    public void deleteItem(TimerModel timerModel) {
        new deleteAsyncTask(appDatabase).execute(timerModel);
    }

    private static class deleteAsyncTask extends AsyncTask<TimerModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final TimerModel... params) {
            db.itemAndPersonModel().deleteTimer(params[0]);
            return null;
        }

    }

}
