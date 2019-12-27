package com.faizal.guiado.handler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler


class NetworkChangeHandler {

    private val networkChangeHandler = Handler()

    private var networkChangeListener: NetworkChangeListener? = null

    private val networkStateChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.action!!, ignoreCase = true)) {
                val state = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)
                networkChangeHandler.post {
                    if (networkChangeListener != null) {
                        networkChangeListener!!.networkChangeReceived(!state)
                    }
                }
            }
        }
    }

    fun registerNetWorkStateBroadCast(context: Context) {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(networkStateChangeReceiver, intentFilter)
    }

    fun setNetworkStateListener(networkChangeListener: NetworkChangeListener) {
        this.networkChangeListener = networkChangeListener
    }

    fun unRegisterNetWorkStateBroadCast(context: Context) {
        try {
            context.unregisterReceiver(networkStateChangeReceiver)
        } catch (ex: Exception) {
        }

    }

    interface NetworkChangeListener {
        fun networkChangeReceived(state: Boolean)
    }
}