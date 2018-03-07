package com.dailyblah.rajanikaparthy.talkingtimer;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dailyblah.rajanikaparthy.roomdb.TimerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Only one screen
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEditText;
    Spinner mSpinnerHour,mSpinnerMinute,mSpinnerSecond;
    long mHours,mMinutes,mSeconds = 0;
    CountDownTimer mCountDownTimer;
    TextView mTextViewTimer;
    Button mButtonStart,mButtonStop;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    AddTimerViewModel addTimerViewModel;
    String timerStr;
    TimerModel deletetimerModel;
    LifecycleOwner myLife;

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
                TimerModel tmdl = new TimerModel(
                        mEditText.getText().toString(),
                        getTimeValue());
                createAlarmAdd(tmdl);
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

        final String[] mMinute = getResources().getStringArray(R.array.values);
        final String[] mSecond = Arrays.copyOf(mMinute,mMinute.length);
        final String[] mHour = Arrays.copyOf(mMinute,12);


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
        addTimerViewModel.getItemAndPersonList().observe(MainActivity.this, new Observer<List<TimerModel>>() {
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
                        createAlarmAdd(deletetimerModel);
                       dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        addTimerViewModel.deleteItem(deletetimerModel);
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
    }

  public String getTimeValue(){
        return (String.valueOf(mHours) +":" + String.valueOf(mMinutes) +":" + String.valueOf(mSeconds));
    }

    public void cleanUp(){
        mEditText.setText("");
        mSpinnerHour.setSelection(0);
        mSpinnerSecond.setSelection(0);
        mSpinnerMinute.setSelection(0);
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

    public void createAlarmAdd(TimerModel tmd)
    {
        addTimerViewModel.addTimer(tmd);
        addTimerViewModel.getData().observe(myLife, new Observer<Long>() {
            public void onChanged(@Nullable final Long aLong) {

                mTextViewTimer.setText("Seconds remaining : " + String.valueOf(aLong));
                if(aLong == 0){
                    mTextViewTimer.setText("Timer Finished");
                }
            }
        });
    }
}
