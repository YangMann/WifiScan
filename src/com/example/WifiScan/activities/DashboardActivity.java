package com.example.WifiScan.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.WifiScan.R;

/**
 * Created by Yang Zhang on 2014/4/14.
 */
public class DashboardActivity extends Activity {

    private static final String TAG = "DashboardActivity";

    private Button btn_direct_measure;
    private Button btn_bluetooth_control;
    private Button btn_bluetooth_listen;
    private Button btn_continuous_measure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        btn_direct_measure = (Button) findViewById(R.id.button_direct_measure);
        btn_bluetooth_control = (Button) findViewById(R.id.button_bluetooth_control);
        btn_bluetooth_listen = (Button) findViewById(R.id.button_bluetooth_listen);
        btn_continuous_measure = (Button) findViewById(R.id.button_continuous);

        btn_direct_measure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DashboardActivity.this, DirectMeasureActivity.class);
                startActivity(intent);
            }
        });

        btn_bluetooth_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DashboardActivity.this, BluetoothControlActivity.class);
                startActivity(intent);
            }
        });

        btn_bluetooth_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DashboardActivity.this, BluetoothListenerActivity.class);
                startActivity(intent);
            }
        });

        btn_continuous_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DashboardActivity.this, ContinuouslyMeasureActivity.class);
                startActivity(intent);
            }
        });
    }

}
