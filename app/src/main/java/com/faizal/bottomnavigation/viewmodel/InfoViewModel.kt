package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.Fragment
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.utils.EnumValidator
import com.faizal.bottomnavigation.utils.Validator
import com.faizal.bottomnavigation.view.FragmentFeedBack
import com.faizal.bottomnavigation.view.FragmentFineRide
import com.faizal.bottomnavigation.view.FragmentInfo
import com.faizal.bottomnavigation.view.FragmentLocationPicker
import com.google.firebase.auth.FirebaseAuth
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.listeners.TimeListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import com.itravis.ticketexchange.utils.TimePickerEvent





class InfoViewModel(private val context: Context, private val fragmentSignin: FragmentInfo) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {
    private val mAuth: FirebaseAuth
    private var networkStateHandler: NetworkChangeHandler? = null


    @get:Bindable
    var dataUsername: String? = null
        set(username) {
            field = username
            notifyPropertyChanged(BR.dataUsername)
        }
    @get:Bindable
    var dataPassword: String? = null
        set(password) {
            field = password
            notifyPropertyChanged(BR.dataPassword)
        }


    @get:Bindable
    var showDate: String? = null
        set(showDate) {
            field = showDate
            notifyPropertyChanged(BR.showDate)
        }

    @get:Bindable
    var showTime: String? = null
        set(showTime) {
            field = showTime
            notifyPropertyChanged(BR.showTime)
        }

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
            val fragment = FragmentLocationPicker()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
    }
    fun feedback() {
        Log.d("tag", "taggg")
        val fragment = FragmentFeedBack()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }


    private fun launchChildFragment(mapFragment: BaseFragment) {
        val bundle = Bundle()
        mapFragment.arguments = bundle
        fragmentSignin.newInstance(1, mapFragment, bundle)
    }

    private fun validateInput(): Boolean {

        if (dataUsername == null && dataPassword == null) {
            return false
        }
        return if (dataUsername!!.length < 1 && dataPassword!!.length < 1) {
            false
        } else Validator().validate(dataUsername, EnumValidator.EMAIL_PATTERN)

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
    fun timePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            TimePickerEvent().onTimePickerClick(fragmentSignin.context!!, object : TimeListener {
                override fun onTimeSet(result: String) {
                    showTime = result;
                }
            })
        }
    }

    @Override
    fun datePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentSignin.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    showDate = (result)
                }
            })
        }
    }

    @Override
    fun setPickUpLocation() = View.OnClickListener() {
        if (!handleMultipleClicks()) {

            val bundle = Bundle()
            fragmentSignin.newInstance(2, FragmentLocationPicker(), bundle)
        }
    }


    @Override
    fun setDropOffLocation() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            val bundle = Bundle()
            fragmentSignin.newInstance(2, FragmentLocationPicker(), bundle)

        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
