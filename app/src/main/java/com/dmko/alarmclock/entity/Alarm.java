package com.dmko.alarmclock.entity;

import java.io.Serializable;
import java.util.Locale;

public class Alarm implements Serializable {
    private int id;
    private int hour;
    private int minute;
    private boolean isActive;

    public Alarm() {
    }

    public Alarm(int hour, int minute, boolean isActive) {
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
    }

    public Alarm(int id, int hour, int minute, boolean isActive) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "id = %d, %02d:%02d, isActive = " + isActive(), getId(), getHour(), getMinute());
    }
}
