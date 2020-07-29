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
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FragmentGameChooser
import com.guiado.grads.view.FragmentNewDiscusssion


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
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg), Toast.LENGTH_LONG).show()
                return@OnClickListener
            } else if (userTitle!!.length < 20) {
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg_discussion), Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            Log.d(TAG, "DocumentSnapshot onSuccess ")
//            val fragment = FragmentGameChooser()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, userTitle)
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));

            fragmentSignin.finish()
            val intent = Intent(fragmentSignin, FragmentGameChooser::class.java)
            intent.putExtra(Constants.POSTAD_OBJECT, userTitle)
            fragmentSignin.startActivity(intent)

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