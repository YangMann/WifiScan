package com.example.WifiScan.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.WifiScan.R;
import com.example.WifiScan.activities.base.BluetoothBaseActivity;
import com.example.WifiScan.services.CommandService;
import com.example.WifiScan.services.WifiScanService;

/**
 * Created by Yang Zhang on 2014/4/14.
 */
public class BluetoothListenerActivity extends BluetoothBaseActivity {

    private static final String TAG = "BluetoothListenerActivity";

    private WifiScanService mWifiScanService;
    private CommandReceiver mCommandReceiver;

    private Button btn_do_measurement;

    private class CommandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle mBundle = intent.getExtras();
            if (mBundle.getString("COMMAND").equals(MESSAGE_START_MEASUREMENT)) {
                mWifiScanService.scan();
                Log.i(TAG, "Listener Received!");
                sendMessage(MESSAGE_MEASUREMENT_DONE);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mWifiScanService = new WifiScanService(this);

        btn_do_measurement = (Button) findViewById(R.id.button1);

        startService(new Intent(BluetoothListenerActivity.this, CommandService.class));
        mCommandReceiver = new CommandReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.example.WifiScan.activities.BluetoothListenerActivity");
        registerReceiver(mCommandReceiver, mIntentFilter);
    }
}
