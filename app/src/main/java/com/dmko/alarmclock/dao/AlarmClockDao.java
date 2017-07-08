package com.dmko.alarmclock.dao;

import com.dmko.alarmclock.entity.Alarm;

import java.util.List;

public interface AlarmClockDao {
    List<Alarm> getAlarms();
    Alarm getAlarmById(int id);
    int addAlarm(Alarm alarm);
    void updateAlarm(Alarm alarm);
    void deleteAlarmById(int id);
}
