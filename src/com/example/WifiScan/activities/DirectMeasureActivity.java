package com.example.WifiScan.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.WifiScan.R;
import com.example.WifiScan.services.WifiScanService;

import java.util.Vector;

public class DirectMeasureActivity extends Activity {

    private WifiScanService wifi_scan = null;
    Vector<String> rss_list = null;
    public static int test_id = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final EditText text_ip = (EditText) findViewById(R.id.ipText);
        final Button button_change_activity = (Button) findViewById(R.id.button1);
        final Button button_clear_list = (Button) findViewById(R.id.button2);

        wifi_scan = new WifiScanService(this);
        test_id = 0;

        button_change_activity.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                test_id += 1;
                wifi_scan.scan();
                rss_list = wifi_scan.getRssList();
                text_ip.setText("Test ID: " + test_id + "\n" + rss_list.toString() + "\n");
            }
        });

        button_clear_list.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                text_ip.setText("");
                test_id = 0;
            }
        });
    }
}
