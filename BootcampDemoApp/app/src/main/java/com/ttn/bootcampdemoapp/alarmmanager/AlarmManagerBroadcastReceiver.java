package com.ttn.bootcampdemoapp.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO Add your action here
        Toast.makeText(context, "Your Alarm is running", Toast.LENGTH_SHORT).show();
    }
}
