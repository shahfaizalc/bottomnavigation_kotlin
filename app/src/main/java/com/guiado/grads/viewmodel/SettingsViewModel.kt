package com.guiado.grads.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.utils.EnumValidator
import com.guiado.grads.utils.Validator
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.view.*
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.listeners.TimeListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import com.itravis.ticketexchange.utils.TimePickerEvent





class SettingsViewModel(private val context: Context, private val fragmentSignin: FragmentSettings) :
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
        if (!handleMultipleClicks()) {
//            val fragment = FragmentLocationPicker()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
    }

    fun inviteFriends(){
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = context.resources.getString(R.string.shareInfo)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"))

    }
    fun privacyClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentPrivacy()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
    }
    fun aboutClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentAbout()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));



        }
    }


    fun feedback() {
        Log.d("tag", "taggg")
//        val fragment = FragmentFeedBack()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
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



    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
