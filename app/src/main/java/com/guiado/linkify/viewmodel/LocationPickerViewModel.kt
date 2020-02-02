package com.guiado.linkify.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.guiado.linkify.R
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.util.MultipleClickHandler
import com.guiado.linkify.view.FragmentLocationPicker
import com.google.firebase.auth.FirebaseAuth
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.utils.DatePickerEvent

class LocationPickerViewModel(private val context: Context, private val fragmentSignin: FragmentLocationPicker) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {
    private val mAuth: FirebaseAuth
    private var networkStateHandler: NetworkChangeHandler? = null


    private var isInternetConnected: Boolean = false

    init {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            // launchChildFragment(FragmentHomePage())
        }
        networkHandler()
    }

    fun signInUserClicked() {

    }

    private fun launchChildFragment(mapFragment: BaseFragment) {
        val bundle = Bundle()
        mapFragment.arguments = bundle
        fragmentSignin.newInstance(1, mapFragment, bundle)
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



    @Override
    fun datePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentSignin.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                }
            })
        }
    }



    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
