package com.dmko.alarmclock.listener;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.util.AlarmManager;

public class SetAlarmListener implements CompoundButton.OnCheckedChangeListener {
    private Alarm alarm;
    private Context context;
    private AlarmManager alarmManager;
    private AlarmClockDao alarmClockDao;

    public SetAlarmListener(Alarm alarm, Context context) {
        this.alarm = alarm;
        this.context = context;
        alarmManager = new AlarmManager(context);
        alarmClockDao = new SQLiteAlarmClockDao(context);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            long time = alarmManager.setAlarm(alarm);
            Toast.makeText(context, getDurationBreakdown(time), Toast.LENGTH_SHORT).show();
            alarm.setActive(true);
            alarmClockDao.updateAlarm(alarm);
        } else {
            alarmManager.cancelAlarm(alarm);
            Toast.makeText(context, "Alarm has been cancelled", Toast.LENGTH_SHORT).show();
            alarm.setActive(false);
            alarmClockDao.updateAlarm(alarm);
        }
    }

    private String getDurationBreakdown(long time){
        int hours   = (int) ((time / (1000*60*60)) % 24);
        int minutes = (int) ((time / (1000*60)) % 60);
        if(hours == 0 && minutes == 0) return "Alarm will fire in less than a minute";
        String result = "Alarm will fire after ";
        if(hours != 0) result += hours + " hours ";
        if(minutes != 0) result += minutes + " minutes ";
        return result;
    }
}
