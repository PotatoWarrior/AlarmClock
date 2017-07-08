package com.dmko.alarmclock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import com.dmko.alarmclock.R;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;

public class AddAlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button add;
    private AlarmClockDao alarmClockDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        add = (Button) findViewById(R.id.addButton);
        alarmClockDao = new SQLiteAlarmClockDao(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmClockDao.addAlarm(new Alarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute(), false));
                Toast.makeText(AddAlarmActivity.this, "Alarm has been added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddAlarmActivity.this, ListOfAlarmsActivity.class);
                startActivity(intent);
            }
        });
    }
}
