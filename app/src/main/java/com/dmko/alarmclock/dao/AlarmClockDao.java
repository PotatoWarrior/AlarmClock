package com.dmko.alarmclock.dao;

import com.dmko.alarmclock.entity.Alarm;

import java.util.List;

public interface AlarmClockDao {
    List<Alarm> getAlarms();
    int addAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);
    void deleteAlarmById(int id);
}
