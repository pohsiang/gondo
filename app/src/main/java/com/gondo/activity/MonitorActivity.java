package com.gondo.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gondo.common.Def;
import com.gondo.common.GondoDeviceBluetoothValueAdapter;
import com.gondo.common.GondoDeviceDataValue;

import java.util.List;


public class MonitorActivity extends Activity {

    private static final String TAG = "MonitorActivity";
    private GondoDeviceBluetoothValueAdapter mGondoDeviceBluetoothValueAdapter;
    private BluetoothGatt mNowBluetoothGatt;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGattCharacteristic mNowBluetoothGattCharacteristic;
    private TextView mDeviceTitleText;
    private TextView mIndexTitleText;
    private TextView mIndexTitleText2;
    private TextView mIndexValueText;
    private TextView mConnectionStatusText;
    private TextView mTempValueText;
    private TextView mTempValueUnitText;
    private TextView mIndexValueUnitText;
    private TextView mIndexValueMinusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        mGondoDeviceBluetoothValueAdapter = new GondoDeviceBluetoothValueAdapter();
        initView();
    }

    protected void onResume(){
        super.onResume();
        mBluetoothDevice = getIntent().getExtras().getParcelable(Def.IntentIndex.BLE_DEVICE);
        mBluetoothDevice.connectGatt(getApplicationContext(), true , mBluetoothGattCallback);
        Log.d(TAG, mBluetoothDevice.getName());
        initValue();
    }

    protected void onStop(){
        super.onStop();
        mBluetoothDevice = null;
        if (mNowBluetoothGattCharacteristic != null && mNowBluetoothGatt != null) {
            mNowBluetoothGatt.setCharacteristicNotification(mNowBluetoothGattCharacteristic, false);
            mNowBluetoothGatt = null;
        }
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitor, menu);
        return true;
    }

    private void initView(){
        mConnectionStatusText = (TextView)findViewById(R.id.monitor_connect_status_text);
        mDeviceTitleText = (TextView)findViewById(R.id.monitor_device_name_text);
        mIndexTitleText = (TextView)findViewById(R.id.monitor_index_title_text);
        mIndexTitleText2 = (TextView)findViewById(R.id.monitor_index_title2_text);
        mIndexValueText = (TextView)findViewById(R.id.monitor_index_value_text);
        mTempValueText = (TextView)findViewById(R.id.monitor_temp_value_text);
        mTempValueUnitText = (TextView)findViewById(R.id.monitor_temp_value_unit_text);
        mIndexValueUnitText = (TextView)findViewById(R.id.monitor_index_value_unit_text);
        mIndexValueMinusText = (TextView)findViewById(R.id.monitor_index_value_minus_text);
    }

    private void initValue(){
        mDeviceTitleText.setText(mBluetoothDevice.getName());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reconnect) {
            mBluetoothDevice.connectGatt(getApplicationContext(), true, mBluetoothGattCallback);
        }

        return super.onOptionsItemSelected(item);
    }

    private BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (newState == BluetoothProfile.STATE_CONNECTED){
                Log.i(TAG, "Bluetooth profile status connected");
                gatt.discoverServices();
                updateConnectionStatus(Def.ConnectStatus.STATUS_CONNECTED);
            }else if(newState == BluetoothProfile.STATE_CONNECTING){
                Log.i(TAG, "Bluetooth profile status connecting");
                updateConnectionStatus(Def.ConnectStatus.STATUS_CONNECTING);
            }else {
                Log.i(TAG, "Bluetooth disconnected");
                updateConnectionStatus(Def.ConnectStatus.STATUS_DISCONNECTED);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.i(TAG, "BluetoothService:" + gatt.getServices());
            List<BluetoothGattService> gattServices = gatt.getServices();
            for (BluetoothGattService gattService : gattServices){
                String uuid = gattService.getUuid().toString();
                Log.i(TAG, "BluetoothService UUID:"+uuid);
                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics){
                    Log.i(TAG, "BluetoothCharacteristic UUID:"+gattCharacteristic.getUuid());
                    if (gattCharacteristic.getUuid().toString().equals(Def.GONDO_DEVICE_BLE_SERVICE_UUID)){
                        Log.i(TAG, "setCharacteristicNotification");
                        gatt.setCharacteristicNotification(gattCharacteristic, true);
                        mNowBluetoothGatt = gatt;
                        mNowBluetoothGattCharacteristic = gattCharacteristic;
                    }
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.v(TAG, "Data sending");
            mGondoDeviceBluetoothValueAdapter.updateRawData(characteristic.getValue());
            updateMonitorValue(mGondoDeviceBluetoothValueAdapter.getGondoDeviceDataValue());
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }


    };

    private void updateConnectionStatus(int status){
        Runnable action;
        if (status == Def.ConnectStatus.STATUS_DISCONNECTED){
            action = new Runnable() {
                @Override
                public void run() {
                    mConnectionStatusText.setText(getResources().getString(R.string.connectionstatus_disconnect));
                    mConnectionStatusText.setTextColor(Color.RED);
                }
            };
        }else if (status == Def.ConnectStatus.STATUS_CONNECTED){
            action = new Runnable() {
                @Override
                public void run() {
                    mConnectionStatusText.setText(getResources().getString(R.string.connectionstatus_connect));
                    mConnectionStatusText.setTextColor(Color.GREEN);
                }
            };

        }else {
            action = new Runnable() {
                @Override
                public void run() {
                    mConnectionStatusText.setText("EXCEPTION");
                    mConnectionStatusText.setTextColor(Color.YELLOW);
                }
            };
        }
        runOnUiThread(action);
    }

    private void updateMonitorValue(final GondoDeviceDataValue gondoDeviceDataValue){
        Runnable action = new Runnable() {
            @Override
            public void run() {

                // Visibility
                mIndexTitleText.setVisibility(View.VISIBLE);
                mIndexValueUnitText.setVisibility(View.VISIBLE);
                mIndexValueText.setVisibility(View.VISIBLE);

                if (gondoDeviceDataValue.getDataType().equals(Def.MeasureIndex.ORP)){
                    mTempValueText.setVisibility(View.INVISIBLE);
                    mTempValueUnitText.setVisibility(View.INVISIBLE);
                }else{
                    mTempValueUnitText.setVisibility(View.VISIBLE);
                    mTempValueText.setVisibility(View.VISIBLE);
                }

                if (gondoDeviceDataValue.getDataType().equals(Def.MeasureIndex.O2)){
                    mIndexTitleText2.setVisibility(View.VISIBLE);

                }else{
                    mIndexTitleText2.setVisibility(View.INVISIBLE);
                }

                if (gondoDeviceDataValue.getDataValuePositiveNegative()){
                    mIndexValueMinusText.setVisibility(View.VISIBLE);
                } else{
                    mIndexValueMinusText.setVisibility(View.INVISIBLE);
                }

                // Text
                mIndexTitleText.setText(gondoDeviceDataValue.getDataType());
                mIndexValueText.setText(String.valueOf(gondoDeviceDataValue.getDataValue()));
                mIndexValueUnitText.setText(gondoDeviceDataValue.getDataValueUnit());
                mTempValueText.setText(String.valueOf(gondoDeviceDataValue.getTempValue()));
                mTempValueUnitText.setText(gondoDeviceDataValue.getTempValueUnit());

                if (gondoDeviceDataValue.getDataType().equals(Def.MeasureIndex.O2)){
                    mIndexTitleText.setText(gondoDeviceDataValue.getDataType().substring(0, 1));
                    mIndexTitleText2.setText("2");
                }
            }
        };
        runOnUiThread(action);
    }
}
