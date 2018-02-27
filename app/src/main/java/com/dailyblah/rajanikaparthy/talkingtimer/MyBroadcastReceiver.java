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

    String mString = "Timer finished";
    @Override
    public void onReceive(final Context context, final Intent intent) {

        mString = intent.getStringExtra("TEXT");
        displayAlert(context.getApplicationContext(), mString);
    }


    private void displayAlert(Context context, String string)
    {
        Intent i=new Intent(context,MyAlertDialog.class);git
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.putExtra("TEXT",string);
        context.startActivity(i);
    }

}