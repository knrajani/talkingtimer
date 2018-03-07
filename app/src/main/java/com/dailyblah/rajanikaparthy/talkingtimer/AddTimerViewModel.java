package com.dailyblah.rajanikaparthy.talkingtimer;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.dailyblah.rajanikaparthy.roomdb.AppDatabase;
import com.dailyblah.rajanikaparthy.roomdb.TimerModel;
import com.dailyblah.rajanikaparthy.talkingtimer.MyBroadcastReceiver;

import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by rajanikaparthy on 2018-03-03.
 */

public class AddTimerViewModel extends AndroidViewModel{

    private AppDatabase appDatabase;
    int mIntPending = 0;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    long longTime = 0;
    private static MutableLiveData<Long> data = new MutableLiveData<Long>();
    private final LiveData<List<TimerModel>> itemAndPersonList;

    public AddTimerViewModel(@NonNull final Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        itemAndPersonList = appDatabase.itemAndPersonModel().getAllTimeredItems();
    }

    public void addTimer(final TimerModel timerModel) {
        if(timerModel.getTimerId()!=null)
        {
            new addAsyncTask(appDatabase).execute(timerModel);
        }
        createAlarm(timerModel);

    }

    public void createAlarm(TimerModel timerModel){
        long mlongSec = TimeStringToLong(timerModel.getTimerTime());
        longTime = mlongSec;
        Context context = this.getApplication().getApplicationContext();
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        String text =  timerModel.getTimerString() + " Timer finished";
        intent.putExtra("TEXT",text);
        mIntPending = (int)System.currentTimeMillis();
        pendingIntent = PendingIntent.getBroadcast(
                this.getApplication().getApplicationContext(),mIntPending, intent, 0);
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mlongSec
                , pendingIntent);

    }


    public long TimeStringToLong(String str){
        long result = 0;
        try{
            String td[] = str.split(":");
            result = Integer.parseInt(td[2]) * 1000 + Integer.parseInt(td[1]) * 60 * 1000 + Integer.parseInt(td[0]) * 60 * 60 * 1000;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public LiveData<Long> getData(){
        new CountDownTimer(longTime, 1000) {
         public void onTick(long millisUntilFinished) {
                //mTextViewTimer.setText("Seconds remaining : " + millisUntilFinished / 1000);
                data.setValue(millisUntilFinished / 1000);
            }
            public void onFinish() {
                //mTextViewTimer.setText("Timer Finished");
                //cleanUp();
            }
        }.start();
       return data;
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
