package com.example.WifiScan.services;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.WifiScan.activities.DirectMeasureActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zhang Yang on 14-3-1.
 *
 */
public class WifiScanService extends DirectMeasureActivity {

    static final String TAG = "WifiScanService";

    static WifiScanService wifi_scan;
    static Object sync = new Object();
    static int TEST_TIME = 1;

    WifiManager wifi_manager;
    private Vector<String> scanned;
    private boolean flag_is_scanning = false;
    private Vector<ScanResult> vector_scan_result;
    private FileOutputStream out;
    private int test_count;
    private String file_name;
    private ListView list_view;
    private ArrayAdapter<String> array_adapter;

    public WifiScanService(Context context) {
        this.wifi_manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.scanned = new Vector<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        this.file_name = "RSS_AP_" + str + ".txt";
    }

    public WifiScanService(Context context, ListView mListView, ArrayAdapter<String> mArrayAdapter) {
        this.wifi_manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.scanned = new Vector<String>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        this.file_name = "RSS_AP_" + str + ".txt";
        this.list_view = mListView;
        this.array_adapter = mArrayAdapter;
    }

    public void scan() {
        startScan();
    }

    public void scan(final String mSSID, final int mTestTime) {
        Log.v(TAG, "Start Scan SSID !!!!");
        this.flag_is_scanning = true;
        Thread thread_scan = new Thread(new Runnable() {
            @Override
            public void run() {
                scanned.clear();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);

                Log.v(TAG, "testID: " + test_id + " TestTime: " + str + " BEGIN\n");

                while (test_count < mTestTime) {
                    performScan(mSSID);
                    test_count += 1;
                }


            }
        });
        thread_scan.start();
    }

    public boolean scanning_status() {
        return flag_is_scanning;
    }

    public Vector<String> getRssList() {
        return scanned;
    }

    private void startScan() {
        Log.v(TAG, "Start Scan !!!!");
        this.flag_is_scanning = true;
        Thread thread_scan = new Thread(new Runnable() {
            @Override
            public void run() {
                scanned.clear();
                test_count = 0;

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);

                Log.v(TAG, "testID: " + test_id + " TestTime: " + str + " BEGIN\n");

                while (test_count < TEST_TIME) {
                    performScan();
                    test_count += 1;
                }


            }
        });

        thread_scan.start();
    }

    private void performScan() {
        if (wifi_manager == null) {
            Log.e(TAG, "wifi_manager == null");
        }

        try {
            if (!wifi_manager.isWifiEnabled()) {
                wifi_manager.setWifiEnabled(true);
            }
            if (test_count == 0) {
                for (int i = 0; i < 4; i++) {
                    wifi_manager.startScan();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    wifi_manager.getScanResults();
                }
            }
            wifi_manager.startScan();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.scanned.clear();
            List<ScanResult> list_scan_result = wifi_manager.getScanResults();
            for (ScanResult ap : list_scan_result) {
                Log.v(TAG, ap.SSID + "\t" + ap.level + "\n");
                writeFile(file_name, ap.SSID + " " + ap.level + "\n");
            }
        } catch (Exception e) {
            this.flag_is_scanning = false;
            this.scanned.clear();
            e.printStackTrace();
        }
    }

    private void performScan(String mSSID) {
        if (wifi_manager == null) {
            Log.e(TAG, "wifi_manager == null");
        }

        try {
            if (!wifi_manager.isWifiEnabled()) {
                wifi_manager.setWifiEnabled(true);
            }
            if (test_count == 0) {
                for (int i = 0; i < 4; i++) {
                    wifi_manager.startScan();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    wifi_manager.getScanResults();
                }
            }
            wifi_manager.startScan();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.scanned.clear();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            List<ScanResult> list_scan_result = wifi_manager.getScanResults();
            for (ScanResult ap : list_scan_result) {
                if (ap.SSID.equals(mSSID)) {
                    Log.v(TAG, ap.SSID + "\t" + ap.level + "\n");
                    writeFile("[CONTINUOUS]" + file_name, str + " " + ap.SSID + " " + ap.level + "\n");
                    // array_adapter.add(str + ap.SSID + " " + ap.level + "\n");
                    // list_view.setAdapter(array_adapter);
                }
            }
        } catch (Exception e) {
            this.flag_is_scanning = false;
            this.scanned.clear();
            e.printStackTrace();
        }
    }

    private void writeFile(String filename, String a) {
        try {
            File file = new File("/sdcard/" + filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile random_file = new RandomAccessFile("/sdcard/" + filename, "rw");
            long file_length = random_file.length();
            random_file.seek(file_length);
            random_file.writeBytes(a);
            random_file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
