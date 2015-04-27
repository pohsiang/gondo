package com.gondo.common;

/**
 * Created by Mike on 3/9/15.
 */
public class GondoDeviceDataValue {

    private static String mDataType;
    private static String mDataValueUnit;
    private static float mDatatValue;
    private static boolean mDataValuePositiveNegative;
    private static float mTempValue;
    private static String mTempValueUnit;

    public float getDataValue(){
        return mDatatValue;
    }

    public String getDataType(){
        return mDataType;
    }

    public boolean getDataValuePositiveNegative(){
        return mDataValuePositiveNegative;
    }

    public float getTempValue(){
        return mTempValue;
    }

    public String getDataValueUnit(){
        return mDataValueUnit;
    }

    public String getTempValueUnit(){
        return mTempValueUnit;
    }

    public void setDataValuePositiveNegative(boolean param){
        mDataValuePositiveNegative = param;
    }

    public void setDataValue(float dataValue){
        mDatatValue = dataValue;
    }

    public void setDataValueUnit(String dataValueUnit){
        mDataValueUnit = dataValueUnit;
    }

    public void setDataType(String dataType){
        mDataType = dataType;
    }

    public void setTempValue(float tempValue){
        mTempValue = tempValue;
    }

    public void setTempValueUnit(String tempValueUnit){
        mTempValueUnit = tempValueUnit;
    }



}
