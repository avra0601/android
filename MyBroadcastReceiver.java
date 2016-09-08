package com.example.dell.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;
import android.location.LocationListener;
import android.location.LocationManager;
/**
 *
 * Created by DELL on 09-01-2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

MainActivity.obj.setListener();
}}