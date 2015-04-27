package com.gondo.component;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gondo.activity.MonitorActivity;
import com.gondo.activity.R;
import com.gondo.common.Def;

/**
 * Created by Mike on 3/2/15.
 */
public class BluetoothDeviceView {

    private Context mContext;
    private BluetoothDevice mBluetoothDevice;
    private View mView;
    private TextView mDeviceNameText;
    private TextView mDeviceIdText;
    public BluetoothDeviceView(Context context, BluetoothDevice bluetoothDevice){
        mBluetoothDevice = bluetoothDevice;
        mContext = context;
        initView();
        initValue();
        setListener();
    }

    private void initView(){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mView = layoutInflater.inflate(R.layout.main_device_list_item, null);
        mDeviceNameText = (TextView)mView.findViewById(R.id.item_bt_device_name);
        mDeviceIdText = (TextView)mView.findViewById(R.id.item_bt_device_id);
    }

    private void initValue(){
        mDeviceNameText.setText(mBluetoothDevice.getName());
        mDeviceIdText.setText(String.valueOf(mBluetoothDevice.getAddress()));
    }

    private void setListener(){
        mView.setOnClickListener(mViewOnClickListener);
    }

    public View getView(){
        return mView;
    }

    private View.OnClickListener mViewOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent startMonitorActivityIntent = new Intent();
            startMonitorActivityIntent.setClass(mContext, MonitorActivity.class);
            startMonitorActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startMonitorActivityIntent.putExtra(Def.IntentIndex.BLE_DEVICE_ADDR, mBluetoothDevice.getAddress());
            startMonitorActivityIntent.putExtra(Def.IntentIndex.BLE_DEVICE, mBluetoothDevice);
            mContext.startActivity(startMonitorActivityIntent);
        }
    };

}
