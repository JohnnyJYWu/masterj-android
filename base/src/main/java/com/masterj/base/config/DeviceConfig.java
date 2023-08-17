package com.masterj.base.config;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.masterj.base.MasterJBase;
import com.masterj.base.log.L;
import com.masterj.base.runtime.MasterJRuntime;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DeviceConfig {

    public static final String WIFI = "wifi";
    public static final String NET_2G = "2G";
    public static final String NET_3G = "3G";
    public static final String NET_4G = "4G";
    public static final String NO_CONNECT = "no_connect";
    public static final String UNKNOWN = "mobile";

    private static DeviceConfig me;

    private static final float normalizeDpi = 160f;

    private DeviceConfig() {
    }

    public static DeviceConfig getInstance() {
        if (me == null) {
            synchronized (DeviceConfig.class) {
                if (me == null) {
                    me = new DeviceConfig();
                }
            }
        }
        return me;
    }

    /**
     * get the file store path
     * if sd card found, storeDir=/sdcard/Android/data/{package.name}
     * or storeDir is in flash
     */
    public File getStoreDir() {
        if (hasSdCard()) {
            if (EXTERNAL_DIR == null) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Android/data/" + AppConfig.INSTANCE.getPackageName();
                EXTERNAL_DIR = new File(path);
            }
            return EXTERNAL_DIR;
        } else {
            return getApp().getApplicationContext().getFilesDir();
        }
    }

    private File EXTERNAL_DIR;

    public boolean hasSdCard() {
        try {
            return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
                    .getExternalStorageState());
        } catch (Throwable e) {
        }
        return false;
    }

    public boolean hasCamera() {
        return getApp().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public boolean hasCameraAutoFocus() {
        return getApp().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
    }

    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public int getDpi() {
        DisplayMetrics metrics = getApp().getResources().getDisplayMetrics();
        int dpi = (int) (metrics.density * getNormalizeDpi());
        return dpi;
    }

    public WindowManager getWindowManager() {
        try {
            return MasterJRuntime.INSTANCE.getCurrentActivity().getWindowManager();
        } catch (Throwable e) {
            return (WindowManager) getApp().getSystemService(Context.WINDOW_SERVICE);
        }
    }

    private String networkDesc; // "wifi" or "mobile"

    public String getNetworkDesc() {
        initNetworkInfo();
        return networkDesc;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = getNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private NetworkInfo networkInfo;

    public NetworkInfo getNetworkInfo() {
        initNetworkInfo();
        return networkInfo;
    }

    public void initNetworkInfo() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            networkDesc = NO_CONNECT;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkDesc = WIFI;
        } else {
            networkDesc = getNetworkClass(networkInfo.getSubtype());
        }
        L.i(this, "network = " + networkDesc);
    }

    public boolean isWifiAvailable() {
        NetworkInfo networkInfo = getNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public int getPlatformCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public String getPlatformStr() {
        return android.os.Build.VERSION.SDK;
    }

    private Application getApp() {
        return MasterJBase.getApplication();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "";
    }

    public String getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NET_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NET_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NET_4G;
            default:
                return UNKNOWN;
        }
    }

    public static float getNormalizeDpi() {
        return normalizeDpi;
    }
}
