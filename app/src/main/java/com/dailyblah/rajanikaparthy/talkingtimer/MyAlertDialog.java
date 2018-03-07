package com.dailyblah.rajanikaparthy.talkingtimer;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;
import com.dailyblah.rajanikaparthy.talkingtimer.R;

import java.util.Locale;

/**
 * Created by rajanikaparthy on 2018-02-14.
 */

public class MyAlertDialog extends AppCompatActivity implements TextToSpeech.OnInitListener{
    String displayStr = "";
    TextToSpeech mTextToSpeech;
    boolean mBooleanStop = false;
    Locale current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current = getResources().getConfiguration().locale;
        Bundle extras = getIntent().getExtras();
        displayStr = extras.getString("TEXT","Timer Finished");
        setContentView(R.layout.activity_my_alert_dialog);
        showAlert();
    }

    public void showAlert(){
        try {
            mTextToSpeech = new TextToSpeech(this, this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        mBooleanStop = false;
        AlertDialog.Builder Builder=new AlertDialog.Builder(this)
                .setMessage(displayStr)
                .setTitle("Timer Finished")
                .setIcon(R.drawable.ic_notifications_active_black_24dp)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                        finish();
                        mBooleanStop = true;
                    }
                });
        AlertDialog alertDialog=Builder.create();
        alertDialog.show();
    }

    @Override
    public void onInit(final int status) {
        if(status != TextToSpeech.ERROR) {
            mTextToSpeech.setLanguage(new Locale(current.getLanguage(),current.getCountry()));
            for(int i = 0; i < 100; i++)
                if(!mBooleanStop)
                {
                    mTextToSpeech.speak(displayStr, TextToSpeech.QUEUE_ADD, null);
                }
        }
        else{
            Toast.makeText(getApplicationContext(),"Error initializing text to speech",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        mBooleanStop = false;
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
