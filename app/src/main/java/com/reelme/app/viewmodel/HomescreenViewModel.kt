package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.pojos.UserModel
import com.reelme.app.view.*

class HomescreenViewModel(private val context: Context, private val fragmentSignin: FragmentHomePage) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDemographics::class.java));
    }


    fun signUpUserClicked() {
       // fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDeleteAccount::class.java));
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
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }

    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        Log.d("Authenticaiton token", "getUserInfo")
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception$e")
        }
    }
}
