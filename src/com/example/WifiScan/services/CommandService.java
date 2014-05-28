package com.example.WifiScan.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.WifiScan.activities.base.BluetoothBaseActivity;

/**
 * Created by Yang Zhang on 2014/4/16.
 */
public class CommandService extends Service {
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public CommandService getService() {
            return CommandService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void sendCommand(String command) {
        if (command.equals(BluetoothBaseActivity.MESSAGE_START_MEASUREMENT)) {
            Intent intent = new Intent();
            intent.putExtra("COMMAND", BluetoothBaseActivity.MESSAGE_START_MEASUREMENT);
            intent.setAction("com.example.WifiScan.activities.BluetoothListenerActivity");
            sendBroadcast(intent);
        } else if (command.equals(BluetoothBaseActivity.MESSAGE_MEASUREMENT_DONE)) {
            Intent intent = new Intent();
            intent.putExtra("COMMAND", BluetoothBaseActivity.MESSAGE_MEASUREMENT_DONE);
            intent.setAction("com.example.WifiScan.activities.BluetoothControlActivity");
            sendBroadcast(intent);
        }
    }
}
