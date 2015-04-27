package com.gondo.common;

import android.util.Log;

/**
 * Created by Mike on 3/9/15.
 */
public class GondoDeviceBluetoothValueAdapter {

    private static String TAG = "GondoDeviceBluetoothValueAdapter";
    private static byte[] mRawData;
    private static GondoDeviceDataValue mGondoDeviceDataValue;

    public void updateRawData(byte[] rawData){
        if (rawData != null) {
            mRawData = rawData;
            mGondoDeviceDataValue = new GondoDeviceDataValue();
            updateData(mRawData[3], mRawData[4], mRawData[7]);
            updateTemp(mRawData[5], mRawData[6], mRawData[7]);
        }else{
            Log.e(TAG, "Bluetooth raw data is null");
        }
    }

    public GondoDeviceDataValue getGondoDeviceDataValue(){
        return mGondoDeviceDataValue;
    }

    // To transfer rawdata to cond, do, orp...etc
    private void updateData(byte byte3, byte byte4, byte byte7){
        float dataValue = Float.valueOf(""+ firstbyteToValue(byte3) + secondbyteToValue(byte4));;
        int typeRuleValue = byte7 % 32;
        int dataValuePositiveNegative = byte7 / 32 % 2;

        switch (dataValuePositiveNegative){
            case 0:
                mGondoDeviceDataValue.setDataValuePositiveNegative(false);
                break;
            case 1:
                mGondoDeviceDataValue.setDataValuePositiveNegative(true);
                break;
        }

        switch(typeRuleValue){
            case 1:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.COND);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.COND_US);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 2:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.COND);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.COND_US);

                //xxxx
                mGondoDeviceDataValue.setDataValue(dataValue);
                break;

            case 3:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.COND);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.COND_MS);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 4:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.COND);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.COND_MS);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 5:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.TDS);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.TDS_PPM);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 6:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.TDS);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.TDS_PPM);

                //xxxx
                mGondoDeviceDataValue.setDataValue(dataValue);
                break;
            case 7:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.TDS);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.TDS_PPT);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 8:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.TDS);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.TDS_PPT);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 9:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.Salt);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.SALT_PPM);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 10:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.Salt);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.SALT_PPM);

                //xxxx
                mGondoDeviceDataValue.setDataValue(dataValue);
                break;
            case 11:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.Salt);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.SALT_PPT);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 12:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.Salt);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.SALT_PPT);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 13:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.PH);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.PH_PH);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 14:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.PH);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.PH_MV);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 15:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.PH);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.PH_MV);

                //xxxx
                mGondoDeviceDataValue.setDataValue(dataValue);
                break;
            case 16:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.ORP);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.ORP_MV);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
            case 17:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.ORP);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.ORP_MV);

                //xxxx
                mGondoDeviceDataValue.setDataValue(dataValue);
                break;
            case 18:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.DO);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.DO_MG);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 19:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.DO);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.DO_ppm);

                //xx.xx
                mGondoDeviceDataValue.setDataValue(dataValue / 100);
                break;
            case 20:
                mGondoDeviceDataValue.setDataType(Def.MeasureIndex.O2);
                mGondoDeviceDataValue.setDataValueUnit(Def.MeasureIndexUnit.O2_percent);

                //xxx.x
                mGondoDeviceDataValue.setDataValue(dataValue / 10);
                break;
        }
    }

    private void updateTemp(byte byte5, byte byte6, byte byte7){
        int CFRuleValue = byte7 / 64 % 2;
        int tempRuleValue = byte7 / 128 % 2;
        float tempValue = Float.valueOf(""+ firstbyteToValue(byte5) + secondbyteToValue(byte6));
        switch (CFRuleValue){
            case 0:
                mGondoDeviceDataValue.setTempValueUnit(Def.TempIndexUnit.TEMP_CELSIUS);
                break;
            case 1:
                mGondoDeviceDataValue.setTempValueUnit(Def.TempIndexUnit.TEMP_FAHREN);
                break;
        }
        switch (tempRuleValue){
            case 0:
                mGondoDeviceDataValue.setTempValue(tempValue / 10);
                break;
            case 1:
                mGondoDeviceDataValue.setTempValue(tempValue);
                break;
        }
    }

    public static String byteToBit(byte b) {
        String resultString =  ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
        return resultString;
    }

    public static String firstbyteToValue(byte b){
        int firstHalfByteValue = 0, secondHalfByteValue = 0;
        if (((b >> 6) & 0x1) == 0x1){
            firstHalfByteValue += 4;
        }
        if (((b >> 5) & 0x1) == 0x1){
            firstHalfByteValue += 2;
        }
        if (((b >> 4) & 0x1) == 0x1){
            firstHalfByteValue += 1;
        }
        if (((b >> 3) & 0x1) == 0x1){
            secondHalfByteValue += 8;
        }
        if (((b >> 2) & 0x1) == 0x1){
            secondHalfByteValue += 4;
        }
        if (((b >> 1) & 0x1) == 0x1){
            secondHalfByteValue += 2;
        }
        if (((b) & 0x1) == 0x1){
            secondHalfByteValue += 1;
        }
        String resultString = String.valueOf(firstHalfByteValue) + String.valueOf(secondHalfByteValue);
        return resultString;
    }

    public static String secondbyteToValue(byte b){
        int firstHalfByteValue = 0, secondHalfByteValue = 0;
        if (((b >> 7) & 0x1) == 0x1){
            firstHalfByteValue += 8;
        }
        if (((b >> 6) & 0x1) == 0x1){
            firstHalfByteValue += 4;
        }
        if (((b >> 5) & 0x1) == 0x1){
            firstHalfByteValue += 2;
        }
        if (((b >> 4) & 0x1) == 0x1){
            firstHalfByteValue += 1;
        }
        if (((b >> 3) & 0x1) == 0x1){
            secondHalfByteValue += 8;
        }
        if (((b >> 2) & 0x1) == 0x1){
            secondHalfByteValue += 4;
        }
        if (((b >> 1) & 0x1) == 0x1){
            secondHalfByteValue += 2;
        }
        if (((b) & 0x1) == 0x1){
            secondHalfByteValue += 1;
        }
        String resultString = String.valueOf(firstHalfByteValue) + String.valueOf(secondHalfByteValue);
        return resultString;
    }

}
