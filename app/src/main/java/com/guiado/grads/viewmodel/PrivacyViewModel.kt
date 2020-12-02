package com.guiado.grads.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.view.FragmentPrivacy
import java.util.*


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
