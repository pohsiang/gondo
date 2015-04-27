package com.gondo.component;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;

/**
 * Created by Mike on 3/2/15.
 */
public class BluetoothDeviceListAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<BluetoothDevice> mBluetoothDevices;
    public BluetoothDeviceListAdapter(Context context, LinkedList<BluetoothDevice> bluetoothDeviceList){
        mContext = context;
        mBluetoothDevices = bluetoothDeviceList;
    }

    public void updateBluetoothDevices(LinkedList<BluetoothDevice> bluetoothDevices){
        mBluetoothDevices = bluetoothDevices;
    }

    @Override
    public int getCount() {
        return mBluetoothDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mBluetoothDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDeviceView deviceView = new BluetoothDeviceView(mContext, mBluetoothDevices.get(position));
        return deviceView.getView();
    }
}
