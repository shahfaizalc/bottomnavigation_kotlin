package com.guiado.akbhar.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.listeners.MultipleClickListener
import com.guiado.akbhar.model.CoachItem
import com.guiado.akbhar.util.GenericValues
import com.guiado.akbhar.util.MultipleClickHandler
import com.guiado.akbhar.BuildConfig
import com.guiado.akbhar.view.FragmentAbout
import java.util.*


class AboutViewModel(private val context: Context, private val fragmentSignin: FragmentAbout) :
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
        listOfCoachings = c.readCourseCategory(context)

    }


    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = BuildConfig.VERSION_NAME
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