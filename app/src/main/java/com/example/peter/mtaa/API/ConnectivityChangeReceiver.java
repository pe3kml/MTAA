package com.example.peter.mtaa.API;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.peter.mtaa.Activity.MainActivity;

/**
 * Created by Peter on 15/Mar/17.
 *
 *
 * Funcionality for connectivity
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    public MainActivity ref_activity;

    public ConnectivityChangeReceiver(MainActivity mainActivity){
        this.ref_activity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        debugIntent(intent, "grokkingandroid");

    }

    private void debugIntent(Intent intent, String tag) {

        Log.v(tag, "action: " + intent.getAction());
        Log.v(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key: extras.keySet()) {
                Log.v(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
            if(extras.get("networkInfo").toString().contains("DISCONNECTED"));
            else
            {
                ref_activity.callUpgrade();
            }

        }
        else {
            Log.v(tag, "no extras");
        }
    }



}
