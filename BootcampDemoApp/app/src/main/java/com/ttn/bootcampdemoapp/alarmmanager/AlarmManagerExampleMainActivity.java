package com.ttn.bootcampdemoapp.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ttn.bootcampdemoapp.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AlarmManagerExampleMainActivity extends AppCompatActivity {

    @BindView(R.id.alarm_time_picker)
    TimePicker mAlarmTimePicker;
    @BindView(R.id.set_alarm_button)
    Button mSetAlarmButton;
    @BindView(R.id.parent_linear_layout)
    LinearLayout mParentLinearLayout;

    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager_example_main);
        mUnbinder = ButterKnife.bind(this);
    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //intent to open the broadcast receiver
        Intent intent = new Intent(this, AlarmManagerBroadcastReceiver.class);

        //pending intent to be passed in alarm manager
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //logic to call the alarm to be run everyday
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.set_alarm_button)
    public void onViewClicked() {
        // fetching the time in millis from the Calendar class to pass in the alarm manager
        // this is the time when the action would be called
        Calendar calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    mAlarmTimePicker.getHour(), mAlarmTimePicker.getMinute(), 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    mAlarmTimePicker.getCurrentHour(), mAlarmTimePicker.getCurrentMinute(), 0);
        }

        setAlarm(calendar.getTimeInMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
