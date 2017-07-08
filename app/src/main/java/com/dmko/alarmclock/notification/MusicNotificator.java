package com.dmko.alarmclock.notification;


import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.dmko.alarmclock.entity.Alarm;

import java.util.HashMap;
import java.util.Map;

public class MusicNotificator {
    private static Map<Integer, Ringtone> map = new HashMap<>();
    private Context context;

    public MusicNotificator(Context context) {
        this.context = context;
    }

    public void start(Alarm alarm) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        map.put(alarm.getId(), ringtone);
        ringtone.play();
    }

    public void stop(Alarm alarm) {
        Ringtone ringtone = map.get(alarm.getId());
        ringtone.stop();
    }
}
