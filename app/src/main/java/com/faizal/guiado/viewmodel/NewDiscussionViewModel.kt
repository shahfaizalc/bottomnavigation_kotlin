package com.faizal.guiado.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.guiado.BR
import com.faizal.guiado.R
import com.faizal.guiado.handler.NetworkChangeHandler
import com.faizal.guiado.listeners.MultipleClickListener
import com.faizal.guiado.util.MultipleClickHandler
import com.faizal.guiado.utils.Constants
import com.faizal.guiado.view.FragmentGameChooser
import com.faizal.guiado.view.FragmentNewDiscusssion


class NewDiscussionViewModel(private val context: Context, private val fragmentSignin: FragmentNewDiscusssion) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false


    init {
        networkHandler()
    }


    @get:Bindable
    var userTitle: String? =  ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userTitle)
        }


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if (userTitle.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.infoMsg), Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            Log.d(TAG, "DocumentSnapshot onSuccess ")
            val fragment = FragmentGameChooser()
            val bundle = Bundle()
            bundle.putString(Constants.POSTAD_OBJECT, userTitle)

            fragment.setArguments(bundle)
            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
        }
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
