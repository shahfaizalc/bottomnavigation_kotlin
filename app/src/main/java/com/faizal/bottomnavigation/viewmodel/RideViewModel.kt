package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.*
import com.google.firebase.auth.FirebaseAuth

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

    fun signout(){
        FirebaseAuth.getInstance().signOut();
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
