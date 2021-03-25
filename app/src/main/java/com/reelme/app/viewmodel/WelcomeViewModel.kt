package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.view.*

class WelcomeViewModel(private val context: Context, private val fragmentSignin: FragmentWelcome) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()
       // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEnterMobile::class.java));
        progressBarVisible = View.VISIBLE

        GenericValues().isUserProfileComplete(fragmentSignin, object : UseInfoGeneralResultListener{
            override fun onSuccess(userInfoGeneral: UserModel) {
                progressBarVisible = View.INVISIBLE
            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEnterMobile::class.java));
            }
        })
    }


    fun signUpUserClicked() {
       // fragmentSignin.finish()
        progressBarVisible = View.VISIBLE

        GenericValues().isUserProfileComplete(fragmentSignin,object : UseInfoGeneralResultListener {
            override fun onSuccess(userInfoGeneral: UserModel) {
                progressBarVisible = View.INVISIBLE
            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEnterMobile::class.java));

            }
        })

        // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentRegistration::class.java));
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

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }


}
