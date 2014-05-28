package com.example.WifiScan.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.example.WifiScan.R;
import com.example.WifiScan.services.WifiScanService;

/**
 * Created by JeffreyZhang on 2014/4/21.
 */
public class ContinuouslyMeasureActivity extends Activity {

    private WifiScanService mWifiScanService;
    private String mSSID;
    private int mMeasurementCount;

    private Handler mHandler;

    private ListView list_continuous_measurement;
    private EditText edit_wifi_name;
    private EditText edit_measurement_count;
    private Button button_continuous_measurement;
    private ArrayAdapter<String> adapter_list_continuous_measurement;

    private static boolean running = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continuous_measure);

        list_continuous_measurement = (ListView) findViewById(R.id.list_continuous_measurement);
        edit_wifi_name = (EditText) findViewById(R.id.edit_wifi_name);
        edit_measurement_count = (EditText) findViewById(R.id.edit_measurement_count);
        button_continuous_measurement = (Button) findViewById(R.id.button_continuous_measure);

        button_continuous_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    if (edit_wifi_name.getText().length() <= 0 || Integer.parseInt(String.valueOf(edit_measurement_count.getText())) <= 0) {
                        Toast.makeText(ContinuouslyMeasureActivity.this, "Please enter a valid SSID", Toast.LENGTH_LONG).show();
                    } else {
                        mSSID = edit_wifi_name.getText().toString();
                        mMeasurementCount = Integer.parseInt(edit_measurement_count.getText().toString());
                        Toast.makeText(ContinuouslyMeasureActivity.this, "Measurement start: " + mSSID, Toast.LENGTH_LONG).show();
                        running = true;
                        adapter_list_continuous_measurement = new ArrayAdapter<String>(ContinuouslyMeasureActivity.this, R.layout.measurement_record);
                        button_continuous_measurement.setText(R.string.button_continuous_measure_stop);
                        mWifiScanService = new WifiScanService(ContinuouslyMeasureActivity.this, list_continuous_measurement, adapter_list_continuous_measurement);
                        mWifiScanService.scan(mSSID, mMeasurementCount);
                    }
                } else {
                    Toast.makeText(ContinuouslyMeasureActivity.this, "Measurement stop", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
