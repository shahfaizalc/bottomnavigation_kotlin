package com.guiado.akbhar.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.guiado.akbhar.R
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.view.*

class RideViewModel(private val context: Context, private val fragmentSignin: FragmentRide) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    fun findClicked() {
        fragmentSignin.mFragmentNavigation.switchTab(4);
    }

    fun beClicked() {
        fragmentSignin.mFragmentNavigation.switchTab(1);
    }


    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(context)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
