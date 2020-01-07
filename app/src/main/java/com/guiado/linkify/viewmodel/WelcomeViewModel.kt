package com.guiado.linkify.viewmodel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.guiado.linkify.R
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.view.*

class WelcomeViewModel(private val context: Context, private val fragmentSignin: FragmentWelcome) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        fragmentSignin.mFragmentNavigation.viewToolbar(false);
    }

    fun signInUserClicked() {

        val fragment = FragmentSignin()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

    }


    fun signUpUserClicked() {
        val fragment = FragmentRegistration()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

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
