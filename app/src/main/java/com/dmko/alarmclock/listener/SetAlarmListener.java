package com.dmko.alarmclock.listener;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.receiver.AlarmReceiver;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class SetAlarmListener implements CompoundButton.OnCheckedChangeListener {
    private Alarm alarm;
    private Context context;
    private AlarmManager alarmManager;
    private AlarmClockDao alarmClockDao;

    public SetAlarmListener(Alarm alarm, Context context) {
        this.alarm = alarm;
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmClockDao = new SQLiteAlarmClockDao(context);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            long time = setAlarm(alarm);
            Toast.makeText(context, getDurationBreakdown(time), Toast.LENGTH_SHORT).show();
            alarm.setActive(true);
            alarmClockDao.updateAlarm(alarm);
        } else {
            cancelAlarm(alarm);
            Toast.makeText(context, "Alarm has been cancelled", Toast.LENGTH_SHORT).show();
            alarm.setActive(false);
            alarmClockDao.updateAlarm(alarm);
        }
    }

    private long setAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
        long triggerTime = getTime(alarm);
        alarmManager.set(AlarmManager.RTC, triggerTime, pendingIntent);
        return triggerTime - new Date().getTime();
    }

    private void cancelAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private long getTime(Alarm alarm) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.HOUR) > alarm.getHour()) {
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else if (calendar.get(Calendar.HOUR) < alarm.getHour()) {
            calendar.set(Calendar.HOUR, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else if (calendar.get(Calendar.MINUTE) < alarm.getMinute()) {
            calendar.set(Calendar.HOUR, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        } else {
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
        }
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }

    private String getDurationBreakdown(long time){
        int hours   = (int) ((time / (1000*60*60)) % 24);
        int minutes = (int) ((time / (1000*60)) % 60);
        String result = "Alarm will fire after ";
        if(hours != 0) result += hours + " hours ";
        if(minutes != 0) result += minutes + " minutes ";
        return result;
    }
}
