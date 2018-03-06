package com.dailyblah.rajanikaparthy.talkingtimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyblah.rajanikaparthy.listitem.AddTimerViewModel;
import com.dailyblah.rajanikaparthy.listitem.RecyclerViewAdapter;
import com.dailyblah.rajanikaparthy.listitem.TimerListViewModel;
import com.dailyblah.rajanikaparthy.roomdb.TimerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEditText;
    Spinner mSpinnerHour,mSpinnerMinute,mSpinnerSecond;
    long mHours,mMinutes,mSeconds = 0;
    CountDownTimer mCountDownTimer;
    /*int mIntPending = 0;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;*/
    TextView mTextViewTimer;
    Button mButtonStart,mButtonStop;


    private TimerListViewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    AddTimerViewModel addTimerViewModel;
    String timerStr;
    TimerModel deletetimerModel;
    LifecycleOwner myLife;
   // InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewTimer = (TextView)findViewById(R.id.text_timer);
        mButtonStart = (Button)findViewById(R.id.button_start);
        mButtonStop = (Button)findViewById(R.id.button_stop);
        mEditText = findViewById(R.id.editText2);


        addTimerViewModel = ViewModelProviders.of(this).get(AddTimerViewModel.class);
        myLife = this;


        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

               // createAlarm(Long.parseLong(getTimeSetting()), mEditText.getText().toString());

                addTimerViewModel.addTimer(new TimerModel(
                        mEditText.getText().toString(),
                        getTimeValue()
                ));
                addTimerViewModel.getData().observe(myLife, new Observer<Long>() {
                    public void onChanged(@Nullable final Long aLong) {

                        mTextViewTimer.setText("Seconds remaining : " + String.valueOf(aLong));
                        if(aLong == 0){
                            mTextViewTimer.setText("Timer Finished");
                        }
                    }
                });

              }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                cleanUp();
            }
        });


        mSpinnerHour = findViewById(R.id.spinner_hour);
        mSpinnerMinute = findViewById(R.id.spinner_minute);
        mSpinnerSecond = findViewById(R.id.spinner_second);

        final String[] mHour = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
        final String[] mMinute = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        final String[] mSecond = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59"};


        CustomAdapter customAdapterHour=new CustomAdapter(getApplicationContext(),mHour);
        mSpinnerHour.setAdapter(customAdapterHour);
        mSpinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                mHours = Long.parseLong(mHour[position]);

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                mHours = 0;
            }
        });


        CustomAdapter customAdapterMinute=new CustomAdapter(getApplicationContext(),mMinute);
        mSpinnerMinute.setAdapter(customAdapterMinute);
        mSpinnerMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                mMinutes = Long.parseLong(mMinute[position]);

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                mMinutes = 0;
            }
        });

        CustomAdapter customAdapterSecond=new CustomAdapter(getApplicationContext(),mSecond);
        mSpinnerSecond.setAdapter(customAdapterSecond);
        mSpinnerSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                mSeconds = Long.parseLong(mSecond[position]);

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                mSeconds = 0;
            }
        });



        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<TimerModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(TimerListViewModel.class);

        viewModel.getItemAndPersonList().observe(MainActivity.this, new Observer<List<TimerModel>>() {
            @Override
            public void onChanged(@Nullable List<TimerModel> itemAndPeople) {
                recyclerViewAdapter.addItems(itemAndPeople);
            }
        });


    }

    @Override
    public void onClick(View v) {
        deletetimerModel = (TimerModel) v.getTag();
        timerStr = " ";
        timerStr = deletetimerModel.getTimerString();
        AlertDialog.Builder Builder=new AlertDialog.Builder(this)
                .setMessage(timerStr)
                .setTitle("Timer ? ")
                .setIcon(R.drawable.ic_notifications_active_black_24dp)
                .setPositiveButton("Set",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        long l = 1;
                        try{
                            l = Long.parseLong(TimeToString(deletetimerModel.getTimerTime()));
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        createAlarm(l,timerStr);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        viewModel.deleteItem(deletetimerModel);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Cancel",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog=Builder.create();
        alertDialog.show();


        //return true;
    }

    public String TimeToString(String str){
        long result = 0;
        try{
            String td[] = str.split(":");
            result = Integer.parseInt(td[2]) * 1000 + Integer.parseInt(td[1]) * 60 * 1000 + Integer.parseInt(td[0]) * 60 * 60 * 1000;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

   /* public String getTimeSetting(){
        long result = 0;
        result = mSeconds * 1000 + mMinutes * 60 * 1000 + mHours * 60 * 60 * 1000;
        return String.valueOf(result);
    }*/

    public String getTimeValue(){
        return (String.valueOf(mHours) +":" + String.valueOf(mMinutes) +":" + String.valueOf(mSeconds));
    }

    public void cleanUp(){
        mEditText.setText("");
        mSpinnerHour.setSelection(0);
        mSpinnerSecond.setSelection(0);
        mSpinnerMinute.setSelection(0);

       /* if(alarmManager!=null && pendingIntent!=null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }*/
        mTextViewTimer.setText("Timer Finished");


    }

    @Override
    public void onDestroy() {
        cleanUp();
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


   /* public void createAlarm(long mlongSec, String timerString){
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        String text =  timerString + " Timer finished";
        intent.putExtra("TEXT",text);
        mIntPending = (int)System.currentTimeMillis();
        pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(),mIntPending, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mlongSec
                , pendingIntent);
       // updatetext(mlongSec);

    }*/


    /*public void updatetext(long mlongSeconds){
        new CountDownTimer(mlongSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextViewTimer.setText("Seconds remaining : " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                mTextViewTimer.setText("Timer Finished");
                cleanUp();
            }
        }.start();
    }*/


}
