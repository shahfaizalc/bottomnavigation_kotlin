package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.*
import com.google.firebase.auth.FirebaseAuth
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.listeners.TimeListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import com.itravis.ticketexchange.utils.TimePickerEvent
import android.view.View.OnClickListener

class StopOversViewModel(private val context: Context, private val fragmentSignin: FragmentStopOvers) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }


    fun signInUserClicked() {

        val fragment = FragmentFineRide()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

    }


    fun signUpUserClicked() {
        val fragment = FragmentFineRide()
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
