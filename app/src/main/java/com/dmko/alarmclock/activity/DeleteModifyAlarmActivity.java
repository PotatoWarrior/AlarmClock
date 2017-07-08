package com.dmko.alarmclock.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dmko.alarmclock.R;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.util.AlarmManager;

public class DeleteModifyAlarmActivity extends AppCompatActivity {
    private AlarmClockDao alarmClockDao;
    private Alarm alarm;
    private TimePicker timePicker;
    private Button save, delete;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_modify_alarm);
        alarmClockDao = new SQLiteAlarmClockDao(this);
        alarm = (Alarm) getIntent().getSerializableExtra("alarm");
        timePicker = (TimePicker) findViewById(R.id.timePickerDM);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(alarm.getHour());
        timePicker.setCurrentMinute(alarm.getMinute());
        save = (Button) findViewById(R.id.saveButton);
        delete = (Button) findViewById(R.id.deleteButton);
        alarmManager = new AlarmManager(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmClockDao.updateAlarm(new Alarm(alarm.getId(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), false));
                alarmManager.cancelAlarm(alarm);
                Toast.makeText(DeleteModifyAlarmActivity.this, "Alarm has been updated", Toast.LENGTH_SHORT).show();
                goToListActivity();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmClockDao.deleteAlarmById(alarm.getId());
                alarmManager.cancelAlarm(alarm);
                Toast.makeText(DeleteModifyAlarmActivity.this, "Alarm has been deleted", Toast.LENGTH_SHORT).show();
                goToListActivity();
            }
        });
    }

    private void goToListActivity() {
        startActivity(new Intent(this, ListOfAlarmsActivity.class));
    }
}
