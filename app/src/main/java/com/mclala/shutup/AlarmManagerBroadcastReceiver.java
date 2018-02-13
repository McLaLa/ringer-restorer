package com.mclala.shutup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.mclala.shutup.myapplication.R;

/**
 * Created by admin on 2017-01-19.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();
        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();


        if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {

            //Make sure this intent has been sent by the one-time timer button.

            msgStr.append("One time Timer : ");

        }

        //Format formatter = new SimpleDateFormat("hh:mm:ss a");

        //msgStr.append(formatter.format(new Date()));
        //Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Toast.makeText(context, context.getString(R.string.ringer_mode_normal), Toast.LENGTH_SHORT).show();

        //Release the lock

        wl.release();

    }

    public void cancelAlarm(Context context) {

        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + SilentActivity.minutes * 60 * 1000, pi);

    }
}
