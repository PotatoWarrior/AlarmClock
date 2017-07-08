package com.dmko.alarmclock.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlarmManager {
    private android.app.AlarmManager alarmManager;
    private Context context;

    public AlarmManager(Context context) {
        this.context = context;
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public long setAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmID", alarm.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerTime = getTime(alarm);
        alarmManager.set(android.app.AlarmManager.RTC, triggerTime, pendingIntent);
        return triggerTime - new Date().getTime();
    }

    public void cancelAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private long getTime(Alarm alarm) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        if (calendar.get(Calendar.HOUR_OF_DAY) > alarm.getHour()) {
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else if (calendar.get(Calendar.HOUR_OF_DAY) < alarm.getHour()) {
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else if (calendar.get(Calendar.MINUTE) < alarm.getMinute()) {
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else {
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        }
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }
}
