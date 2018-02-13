package com.mclala.shutup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import com.mclala.shutup.myapplication.R;

/**
 * Created by admin on 2017-01-18.
 */

public class NotificationActionReceiver extends BroadcastReceiver {

    private AlarmManagerBroadcastReceiver alarm;

    @Override
    public void onReceive(Context context, Intent intent) {

        alarm = new AlarmManagerBroadcastReceiver();

        String action = intent.getAction();
        if (action.equals("SILENT_RINGER_MODE")) {
            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context, context.getString(R.string.ringer_mode_normal), Toast.LENGTH_SHORT).show();

            cancelRepeatingTimer(context);
        }
    }

    public void cancelRepeatingTimer(Context context) {

        if (alarm != null) {
            alarm.cancelAlarm(context);
        } else {
            //Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }

    }
}
