package com.hudun.wifilibrary.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hudun.wifilibrary.listener.NetworkListener
import com.hudun.wifilibrary.util.NetworkUtil
import com.hudun.wifilibrary.util.Util

/**
 * Created by Administrator on 2017/7/7/007.
 * @author xiaoxiaoying
 */
class WifiReceiver : BroadcastReceiver() {
    private var mNetworkListener: NetworkListener? = null
    fun setNetworkListener(mNetworkListener: NetworkListener) {
        this.mNetworkListener = mNetworkListener
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        val action = p1!!.action
        Log.i("yang","action = " + p1.action)
        when (action) {
            Util.WIFI_ACTION -> {
                if (!NetworkUtil(p0!!).isWifi && mNetworkListener != null) {
                    mNetworkListener!!.wifiStop()
                }
            }
        }

    }

}