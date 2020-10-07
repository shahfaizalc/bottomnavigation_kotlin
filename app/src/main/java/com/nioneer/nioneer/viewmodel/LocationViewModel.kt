package com.nioneer.nioneer.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.nioneer.nioneer.R
import com.nioneer.nioneer.handler.NetworkChangeHandler
import com.nioneer.nioneer.view.FragmentLocation

class LocationViewModel(private val context: Context, private val fragmentHome: FragmentLocation) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {


    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    var colorResource = MutableLiveData<Boolean>()
    init {
        colorResource.value = false;
        networkHandler()
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