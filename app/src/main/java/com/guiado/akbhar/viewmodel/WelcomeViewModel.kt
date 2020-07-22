package com.guiado.akbhar.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.guiado.akbhar.R
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.view.*

class WelcomeViewModel(private val context: Context, private val fragmentSignin: FragmentWelcome) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    fun signInUserClicked() {
        fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentSignin::class.java));
    }


    fun signUpUserClicked() {
        fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentRegistration::class.java));

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