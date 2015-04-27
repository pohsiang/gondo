package com.gondo.common;

/**
 * Created by Mike on 3/9/15.
 */
public class Def {

    public static String GONDO_DEVICE_BLE_SERVICE_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";

    public static class IntentIndex{
        public static String BLE_DEVICE = "ble_device";
        public static String BLE_DEVICE_ADDR = "ble_device_addr";
        public static String BLE_DEVICE_UUID = "ble_device_uuid";
    }

    public static class ConnectStatus{
        public static int STATUS_DISCONNECTED = 0;
        public static int STATUS_CONNECTING = 1;
        public static int STATUS_CONNECTED = 2;
    }

    public static class MeasureIndex{
        public static String COND = "COND";
        public static String DO = "DO";
        public static String ORP = "ORP";
        public static String TDS = "TDS";
        public static String Salt = "Salt";
        public static String O2 = "O2";
        public static String PH = "pH";
    }

    public static class MeasureIndexUnit{
        public static String COND_US = "us";
        public static String COND_MS = "ms";
        public static String TDS_PPM = "ppm";
        public static String TDS_PPT = "ppt";
        public static String SALT_PPM = "ppm";
        public static String SALT_PPT = "ppt";
        public static String PH_PH = "pH";
        public static String PH_MV = "mV";
        public static String ORP_MV = "mV";
        public static String DO_MG = "mg/l";
        public static String DO_ppm = "ppm";
        public static String O2_percent = "%";
    }

    public static class TempIndexUnit{
        public static String TEMP_CELSIUS = (char) 0x00B0+"C";
        public static String TEMP_FAHREN = (char) 0x00B0+"F";
    }

}
