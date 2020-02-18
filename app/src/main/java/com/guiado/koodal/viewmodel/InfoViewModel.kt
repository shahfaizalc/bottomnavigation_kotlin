package com.guiado.koodal.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.koodal.BR
import com.guiado.koodal.R
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.handler.NetworkChangeHandler
import com.guiado.koodal.util.MultipleClickHandler
import com.guiado.koodal.utils.EnumValidator
import com.guiado.koodal.utils.Validator
import com.google.firebase.auth.FirebaseAuth
import com.guiado.koodal.view.*
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
//            val fragment = FragmentLocationPicker()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
    }

    fun savedDiscussionsClicked() {
        if (!handleMultipleClicks()) {
            val fragment = FragmentSavedDiscussions()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
    }
    fun savedEventsClicked() {
        if (!handleMultipleClicks()) {
            val fragment = FragmentSavedEvents()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));

        }
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
