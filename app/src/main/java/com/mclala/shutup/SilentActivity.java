package com.mclala.shutup;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mclala.shutup.myapplication.R;

/**
 * Created by admin on 2017-01-18.
 */

public class SilentActivity extends Activity {

    public static int minutes = 0;
    public AlarmManagerBroadcastReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent);

        alarm = new AlarmManagerBroadcastReceiver();

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textView = (TextView) findViewById(R.id.textView);

        minutes = seekBar.getProgress();
        textView.setText(getString(R.string.turn_ringer_on_in) + " " + minutes + " " + getString(R.string.minutes));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                minutes = progress;

                if (progress == 0)
                    minutes = 1;

                textView.setText(getString(R.string.turn_ringer_on_in) + " " + minutes + " " + getString(R.string.minutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(SilentActivity.this, "Silent in minutes: " + minutes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonClicked(View view) {
        Toast.makeText(SilentActivity.this, getString(R.string.ringer_silent_for) + " " + minutes + " " +
                getString(R.string.minutes), Toast.LENGTH_SHORT).show();

        cancelRepeatingTimer(view);

        Intent resultIntent = new Intent("SILENT_RINGER_MODE");
        PendingIntent resultPendingIntent = PendingIntent.getBroadcast(this, 0, resultIntent, 0);

        Notification.Builder mBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.ringer_silent_for) + " " + minutes + " " + getString(R.string.minutes))
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentText(getString(R.string.select_to_turn_it_on_now));

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

        onetimeTimer(view);
        finish();
    }

    public void onetimeTimer(View view) {
        Context context = this.getApplicationContext();
        if (alarm != null) {
            alarm.setOnetimeTimer(context);
        } else {
            //Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelRepeatingTimer(View view) {
        Context context = this.getApplicationContext();
        if (alarm != null) {
            alarm.cancelAlarm(context);
        } else {
            //Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }

    }

}


