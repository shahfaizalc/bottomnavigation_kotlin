package com.nioneer.nioneer.rss.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * To check network availability
 */
class HasNetwork {

    companion object {

        /**
         * Check network state
         */
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return (activeNetwork != null && activeNetwork.isConnected)

        }
    }
}