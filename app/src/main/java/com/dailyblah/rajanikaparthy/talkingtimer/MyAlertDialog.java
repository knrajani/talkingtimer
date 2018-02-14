package com.dailyblah.rajanikaparthy.talkingtimer;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.Locale;

/**
 * Created by rajanikaparthy on 2018-02-14.
 */

public class MyAlertDialog extends AppCompatActivity implements TextToSpeech.OnInitListener{
    String displayStr = "";
    TextToSpeech mTextToSpeech;
    boolean mBooleanStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        displayStr = extras.getString("TEXT","Timer finished");
        setContentView(R.layout.activity_my_alert_dialog);
        showAlert();
        mTextToSpeech=new TextToSpeech(this, this);
    }

    public void showAlert(){
        mBooleanStop = false;
        AlertDialog.Builder Builder=new AlertDialog.Builder(this)
                .setMessage(displayStr)
                .setTitle("Timer Finished")
                .setIcon(android.R.drawable.ic_dialog_alert)
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
            mTextToSpeech.setLanguage(Locale.UK);
            for(int i = 0; i < 100; i++)
            if(!mBooleanStop)
            {
                mTextToSpeech.speak(displayStr, TextToSpeech.QUEUE_ADD, null);
            }

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
