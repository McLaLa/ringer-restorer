package com.mclala.shutup;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created by admin on 2017-01-11.
 */

public class AudioDetectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Intent i = new Intent();

        if (audio.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            Log.d("tag", "RINGER_MODE_NORMAL");

            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
            nMgr.cancel(0);

        } else if (audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT ||
                audio.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            Log.d("tag", "RINGER_MODE_SILENT OR RINGER_MODE_VIBRATE");

            i.setClass(context, SilentActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }

}
