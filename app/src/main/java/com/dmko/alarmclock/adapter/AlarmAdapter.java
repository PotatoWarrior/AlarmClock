package com.dmko.alarmclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.dmko.alarmclock.R;
import com.dmko.alarmclock.entity.Alarm;
import com.dmko.alarmclock.listener.SetAlarmListener;
import java.util.List;
import java.util.Locale;

public class AlarmAdapter extends BaseAdapter{
    private Context context;
    private List<Alarm> alarms;
    private LayoutInflater layoutInflater;

    public AlarmAdapter(Context context, List<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.alarm_item, parent, false);
        }
        TextView triggerTime = (TextView) view.findViewById(R.id.triggerTimeTextView);
        ToggleButton setActive = (ToggleButton) view.findViewById(R.id.setActiveToggleButton);

        Alarm alarm = (Alarm) getItem(position);

        setActive.setOnCheckedChangeListener(new SetAlarmListener(alarm, context));
        triggerTime.setText(String.format(Locale.US, "%02d:%02d", alarm.getHour(), alarm.getMinute()));
        setActive.setChecked(alarm.isActive());

        return view;
    }
}
