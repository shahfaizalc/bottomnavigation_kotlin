package com.guiado.linkify.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.linkify.BR
import com.guiado.linkify.R
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.listeners.MultipleClickListener
import com.guiado.linkify.model2.Profile
import com.guiado.linkify.util.GenericValues
import com.guiado.linkify.util.MultipleClickHandler
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.view.*
import org.greenrobot.eventbus.EventBus
import com.guiado.linkify.Events.MyCustomEvent
import org.greenrobot.eventbus.Subscribe


class PhotoViewModel(private val context: Context, private val fragmentSignin: FragmentPhoto) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    /*
   Method will act as the event handler for MyCustomEvent.kt
   */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        val profile = event.data.address
        val addressVal = profile!!.locationname +"\n "+profile.streetName+"\n "+profile.town
        userAddress = addressVal
        Log.d("dddd","dddd "+ userTitle)

    }

    var imgUrl =""

    @get:Bindable
    var userName: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userName)

        }

    @get:Bindable
    var userEmail: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)

        }


    @get:Bindable
    var userTitle: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userTitle)

        }

    @get:Bindable
    var userPhone: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userPhone)

        }

    @get:Bindable
    var userDesc: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userDesc)

        }

    @get:Bindable
    var userAddress: String = "Your Address"
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    @get:Bindable
    var userMoreInfo: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userMoreInfo)

        }

    @get:Bindable
    var keys: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.keys)

        }

    fun datePickerClick() = View.OnClickListener {

        showToast(R.string.network_ErrorMsg)
        Log.d("dddd","dddd "+ userTitle)

    }

    fun updateAddress()=View.OnClickListener{
        EventBus.getDefault().register(this);

        val profile = Profile()
        profile.name = userName
        profile.email = userEmail
        profile.phone = userPhone
        profile.title = userTitle
        profile.desc = userDesc
        profile.moreInformation = userMoreInfo
        val fragment = FragmentAddress()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        //fragment.setArguments(bundle)
        //fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
        userAddress = "DDUE"
        Log.d("dddd","dddd "+ userTitle)


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

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}
