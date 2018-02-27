package com.dailyblah.rajanikaparthy.talkingtimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText mEditText;
    Spinner mSpinnerHour,mSpinnerMinute,mSpinnerSecond;
    long mHours,mMinutes,mSeconds = 0;
    CountDownTimer mCountDownTimer;
    int mIntPending = 0;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    TextView mTextViewTimer;
    Button mButtonStart,mButtonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewTimer = (TextView)findViewById(R.id.text_timer);
        mButtonStart = (Button)findViewById(R.id.button_start);
        mButtonStop = (Button)findViewById(R.id.button_stop);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mEditText = findViewById(R.id.editText2);
               // mEditText.setFocusable(false);
                createAlarm(Long.parseLong(getTimeSetting()));
               /* mButtonStart.setEnabled(false);
                mButtonStop.setEnabled(true);*/
               // mEditText.setFocusable(true);

              }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               /* mButtonStart.setEnabled(true);
                mButtonStop.setEnabled(false);*/


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
    }


    public String getTimeSetting(){
        long result = 0;
        result = mSeconds * 1000 + mMinutes * 60 * 1000 + mHours * 60 * 60 * 1000;
        return String.valueOf(result);
    }

    public void cleanUp(){
        mEditText.setText("");
        mSpinnerHour.setSelection(0);
        mSpinnerSecond.setSelection(0);
        mSpinnerMinute.setSelection(0);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        mTextViewTimer.setText("Timer Finished");
       /* mButtonStart.setEnabled(true);
        mButtonStop.setEnabled(false);*/

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


    public void createAlarm(long mlongSec){
        updatetext(mlongSec);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        String text =  mEditText.getText().toString() + " Timer finished";
        intent.putExtra("TEXT",text);
        mIntPending = (int)System.currentTimeMillis();
        pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(),mIntPending, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mlongSec
                , pendingIntent);

    }


    public void updatetext(long mlongSeconds){
        new CountDownTimer(mlongSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextViewTimer.setText("Seconds remaining : " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                mTextViewTimer.setText("Timer Finished");
                cleanUp();
            }
        }.start();
  }


}
