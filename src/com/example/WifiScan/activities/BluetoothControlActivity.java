package com.example.WifiScan.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.WifiScan.R;
import com.example.WifiScan.activities.base.BluetoothBaseActivity;
import com.example.WifiScan.services.CommandService;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Yang Zhang on 2014/4/14.
 */
public class BluetoothControlActivity extends BluetoothBaseActivity {

    private static final String TAG ="BluetoothControlActivity";

    private CommandReceiver mCommandReceiver;

    private Button btn_measure;
    private TextView txt_control_status;
    private ListView list_measurement_record;
    private ArrayAdapter<String> adapter_list_measurement_record;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");

    private class CommandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle mBundle = intent.getExtras();
            if (mBundle.getString("COMMAND").equals(MESSAGE_MEASUREMENT_DONE)) {
                txt_control_status.setText(R.string.measurement_done);
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                adapter_list_measurement_record.add(str);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);

        btn_measure = (Button) findViewById(R.id.button_measure);
        txt_control_status = (TextView) findViewById(R.id.text_control_status);
        list_measurement_record = (ListView) findViewById(R.id.list_measurement_record);
        adapter_list_measurement_record = new ArrayAdapter<String>(this, R.layout.measurement_record);

        startService(new Intent(BluetoothControlActivity.this, CommandService.class));
        mCommandReceiver = new CommandReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.example.WifiScan.activities.BluetoothControlActivity");
        registerReceiver(mCommandReceiver, mIntentFilter);

        btn_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                txt_control_status.setText("Processing...");
                sendMessage(MESSAGE_START_MEASUREMENT);
                Log.d(TAG, "MESSAGE_START_MEASUREMENT");
            }
        });

    }
}
