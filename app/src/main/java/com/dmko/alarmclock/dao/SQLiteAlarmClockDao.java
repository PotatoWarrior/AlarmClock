package com.dmko.alarmclock.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dmko.alarmclock.entity.Alarm;

import java.util.ArrayList;
import java.util.List;


public class SQLiteAlarmClockDao extends SQLiteOpenHelper implements AlarmClockDao {
    public static final String TABLE_NAME = "alarm_table";
    public static final String COL0 = "ID";
    public static final String COL1 = "hour";
    public static final String COL2 = "minute";
    public static final String COL3 = "is_active";
    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " INTEGER, " + COL2 + " INTEGER, " + COL3 + " INTEGER)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteAlarmClockDao(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public List<Alarm> getAlarms() {
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Alarm> alarms = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Alarm alarm = new Alarm();
            alarm.setId(cursor.getInt(0));
            alarm.setHour(cursor.getInt(1));
            alarm.setMinute(cursor.getInt(2));
            alarm.setActive(cursor.getInt(3) != 0);
            alarms.add(alarm);
        }
        return alarms;
    }

    @Override
    public int addAlarm(Alarm alarm) {
        this.getWritableDatabase().insert(TABLE_NAME, null, convertAlarmToContentValues(alarm));
        return 0;
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        this.getWritableDatabase().update(TABLE_NAME, convertAlarmToContentValues(alarm), COL0 + " = " + alarm.getId(), null);
    }

    @Override
    public void deleteAlarmById(int id) {
        this.getWritableDatabase().delete(TABLE_NAME, COL0 + " = " + id, null);
    }

    private ContentValues convertAlarmToContentValues(Alarm alarm){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, alarm.getHour());
        contentValues.put(COL2, alarm.getMinute());
        contentValues.put(COL3, alarm.isActive() ? 1 : 0);
        return contentValues;
    }

}


























