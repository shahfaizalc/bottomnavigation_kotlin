package com.reelme.realme.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.reelme.realme.R
import com.reelme.realme.handler.NetworkChangeHandler
import com.reelme.realme.view.*

class HobbiesViewModel(private val context: Context, private val fragmentSignin: FragmentHobbies) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    fun signInUserClicked() {
     //   fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomescreen::class.java));
    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomescreen::class.java));

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