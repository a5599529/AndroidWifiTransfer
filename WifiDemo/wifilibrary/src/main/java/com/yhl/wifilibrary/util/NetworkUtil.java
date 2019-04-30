package com.yhl.wifilibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by yhl on 2016/6/15.
 *
 * @author yhl
 */
public class NetworkUtil {
    private ConnectivityManager connectivityManager;
    private boolean isConnected;
    private boolean isWifi;
    private boolean isMobile;

    public NetworkUtil(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        executeNetwork();
    }


    /**
     * 获取网络状态
     *
     * @return boolean
     */
    private boolean getNetworkConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 获取WiFi状态
     *
     * @return boolean
     */
    private boolean getWifiConnected() {
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 获取移动网络
     *
     * @return boolean
     */
    private boolean getMobileConnected() {
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isAvailable();
    }

    private void setWifi(boolean isWifi) {
        this.isWifi = isWifi;
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public boolean isMobile() {
        return isMobile;
    }

    private void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    private void executeNetwork() {
        boolean connected = getNetworkConnected();
        boolean wifi = getWifiConnected();
        boolean isMobile = getMobileConnected();
        setWifi(wifi);
        setConnected(connected);
        setMobile(isMobile);
    }

    public static String getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = wifiManager.getConnectionInfo();
        int ip = mWifiInfo.getIpAddress();
        return intToIp(ip);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
