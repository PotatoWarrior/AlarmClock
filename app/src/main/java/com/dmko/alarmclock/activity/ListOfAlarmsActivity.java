package com.dmko.alarmclock.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dmko.alarmclock.R;
import com.dmko.alarmclock.adapter.AlarmAdapter;
import com.dmko.alarmclock.dao.AlarmClockDao;
import com.dmko.alarmclock.dao.SQLiteAlarmClockDao;
import com.dmko.alarmclock.entity.Alarm;

import java.util.List;

public class ListOfAlarmsActivity extends AppCompatActivity {
    private ListView alarmsListView;
    private AlarmClockDao alarmClockDao;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_alarms);
        alarmsListView = (ListView) findViewById(R.id.alarmsListView);
        alarmClockDao = new SQLiteAlarmClockDao(this);
        AlarmAdapter alarmAdapter = new AlarmAdapter(this, alarmClockDao.getAlarms());
        alarmsListView.setAdapter(alarmAdapter);
        add = (Button) findViewById(R.id.AddButtonList);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListOfAlarmsActivity.this, AddAlarmActivity.class));
            }
        });
        alarmsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alarm alarm = (Alarm) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListOfAlarmsActivity.this, DeleteModifyAlarmActivity.class);
                intent.putExtra("alarm", alarm);
                startActivity(intent);
            }
        });
    }
}
