package com.dmko.alarmclock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dmko.alarmclock.R;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.notification.MusicNotificator;

public class ClockActivity extends AppCompatActivity {
    private Button goToList, cancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        goToList = (Button) findViewById(R.id.goToListButton);
        cancelAlarm = (Button) findViewById(R.id.cancelAlarmButton);
        cancelAlarm.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        final Alarm alarm = (Alarm) intent.getSerializableExtra("alarm");
        if(alarm != null){
            cancelAlarm.setVisibility(View.VISIBLE);
            cancelAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MusicNotificator(ClockActivity.this).stop(alarm);
                    cancelAlarm.setVisibility(View.INVISIBLE);
                }
            });
        }
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClockActivity.this, ListOfAlarmsActivity.class));
            }
        });
    }
}
