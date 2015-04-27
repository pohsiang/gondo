package com.gondo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.gondo.component.BluetoothDeviceListAdapter;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MeterListActivity extends Activity {

    private final static String TAG = "MeterListActivity";
    private static final int REQUEST_ENABLE_BT = 2;
    private static int SCAN_PERIOD = 10000;
    private static boolean SCAN_STATUS = false;
    private LinkedList<BluetoothDevice> mBluetoothDevices;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;
    private BluetoothDeviceListAdapter mBluetoothDeviceListAdapter;
    private ListView mDeviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_list);
        initView();
        initValue();
        initBluetooth();
    }

    protected void onResume() {
        super.onResume();

        if (mBluetoothAdapter == null) {
            initBluetooth();
        }
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    protected void onStop() {
        super.onStop();
        stopBluetoothScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meter_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_start_scan) {
            if (mBluetoothAdapter == null) {
                initBluetooth();
            }
            if (mBluetoothAdapter.isEnabled()) {
                startBluetoothScan();
            } else {
                Log.e(TAG, "No Bluetooth enable, open bluetooth");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            return true;
        }

        if (id == R.id.menu_stop_scan) {
            stopBluetoothScan();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initBluetooth() {
        Log.i(TAG, "Bluetooth init");
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (Build.VERSION.SDK_INT >= 21) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
    }

    private void startBluetoothScan() {
        if (SCAN_STATUS == false) {
            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                startApi21BluetoothScan();
            }
            SCAN_STATUS = true;
        }
    }

    private void stopBluetoothScan() {
        if (SCAN_STATUS == true) {
            if (Build.VERSION.SDK_INT < 21) {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            } else {
                stopApi21BluetoothScan();
            }
            SCAN_STATUS = false;
        }
    }

    @TargetApi(21)
    private void startApi21BluetoothScan() {
        initScanCallback();
        ScanSettings settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
        List<ScanFilter> filters = new ArrayList<ScanFilter>();
        if (mBluetoothLeScanner != null) {
            mBluetoothLeScanner.startScan(filters, settings, mScanCallback);
        } else {
            Log.e(TAG, "BluetoothLeScanner is null");
        }

    }

    @TargetApi(21)
    private void stopApi21BluetoothScan() {
        if (mBluetoothLeScanner != null) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        } else {
            Log.e(TAG, "BluetoothLeScanner is null");
        }
    }

    private void initView() {
        mDeviceListView = (ListView) findViewById(R.id.main_bt_device_list);
    }

    private void initValue() {
        mBluetoothDevices = new LinkedList<BluetoothDevice>();
        mBluetoothDeviceListAdapter = new BluetoothDeviceListAdapter(getApplicationContext(), mBluetoothDevices);
        mDeviceListView.setAdapter(mBluetoothDeviceListAdapter);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

            if (mBluetoothDevices.size() > 0) {
                int searchResult = mBluetoothDevices.indexOf(device);
                Log.i(TAG, "scan result1");
                if (searchResult < 0) {
                    Log.i(TAG, "scan result");
                    if (device.getName().substring(0, 5).equals(getResources().getString(R.string.app_name))) {
                        mBluetoothDevices.add(device);
                        mBluetoothDeviceListAdapter.updateBluetoothDevices(mBluetoothDevices);
                        mBluetoothDeviceListAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (device.getName().substring(0, 5).equals(getResources().getString(R.string.app_name))) {
                    Log.i(TAG, "scan result2");
                    mBluetoothDevices.add(device);
                    mBluetoothDeviceListAdapter.updateBluetoothDevices(mBluetoothDevices);
                    mBluetoothDeviceListAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @TargetApi(21)
    private void initScanCallback() {
        mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.i(TAG, "Find device:" + mBluetoothDevices.size());
                if (mBluetoothDevices.size() > 0) {
                    int searchResult = mBluetoothDevices.indexOf(result.getDevice());
                    Log.v(TAG, "search result:" + searchResult);
                    if (searchResult < 0) {
                        Log.i(TAG, "Add new device");
                        if (result.getDevice().getName().substring(0, 5).equals(getResources().getString(R.string.app_name))) {
                            mBluetoothDevices.add(result.getDevice());
                            mBluetoothDeviceListAdapter.updateBluetoothDevices(mBluetoothDevices);
                            mBluetoothDeviceListAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (result.getDevice().getName().substring(0, 5).equals(getResources().getString(R.string.app_name))) {
                        mBluetoothDevices.add(result.getDevice());
                        mBluetoothDeviceListAdapter.updateBluetoothDevices(mBluetoothDevices);
                        mBluetoothDeviceListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };
    }

}
