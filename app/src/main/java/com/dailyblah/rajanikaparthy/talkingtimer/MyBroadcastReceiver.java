package com.dailyblah.rajanikaparthy.talkingtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by rajanikaparthy on 2018-02-14.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{//} implements TextToSpeech.OnInitListener{

    //TextToSpeech mTextToSpeech;
    String mString = "Timer finished";
    @Override
    public void onReceive(final Context context, final Intent intent) {
       // mTextToSpeech=new TextToSpeech(context.getApplicationContext(), this);
        mString = intent.getStringExtra("TEXT");
        //Toast.makeText(context, mString, Toast.LENGTH_LONG).show();
        displayAlert(context.getApplicationContext(), mString);
    }

  /*  @Override
    public void onInit(final int status) {
        if(status != TextToSpeech.ERROR) {
            mTextToSpeech.setLanguage(Locale.UK);
            mTextToSpeech.speak(mString, TextToSpeech.QUEUE_FLUSH, null);
        }
    }*/

    private void displayAlert(Context context, String string)
    {
        Intent i=new Intent(context,MyAlertDialog.class);
        i.putExtra("TEXT",string);
        context.startActivity(i);
    }

}