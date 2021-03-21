package com.reelme.realme.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.reelme.realme.BR
import com.reelme.realme.R
import com.reelme.realme.handler.NetworkChangeHandler
import com.reelme.realme.listeners.MultipleClickListener
import com.reelme.realme.util.GenericValues
import com.reelme.realme.util.MultipleClickHandler
import com.reelme.realme.view.FragmentPrivacy


class PrivacyViewModel(private val context: Context, private val fragmentSignin: FragmentPrivacy) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false


    init {
        networkHandler()
        readAutoFillItems()
    }

    private fun readAutoFillItems() {
        val c = GenericValues()

    }



    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)

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
