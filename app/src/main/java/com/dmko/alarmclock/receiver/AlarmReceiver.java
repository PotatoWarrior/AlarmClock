package com.dmko.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dmko.alarmclock.activity.ClockActivity;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.notification.MusicNotificator;

public class AlarmReceiver extends BroadcastReceiver {
    AlarmClockDao alarmClockDao;
    @Override
    public void onReceive(Context context, Intent intent) {
        alarmClockDao = new SQLiteAlarmClockDao(context);
        int id = intent.getIntExtra("alarmID", -1);
        Alarm alarm = alarmClockDao.getAlarmById(id);
        alarm.setActive(false);
        alarmClockDao.updateAlarm(alarm);
        new MusicNotificator(context).start(alarm);
        Intent i = new Intent(context, ClockActivity.class);
        i.putExtra("alarm", alarm);
        context.startActivity(i);
    }
}
