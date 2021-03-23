package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.pojos.UserModel
import com.reelme.app.view.*

class RefferalMobileViewModel(private val context: Context, private val fragmentSignin: FragmentReferralMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
      //  fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));
    }


    fun signUpUserClicked() {
       // fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));

    }
    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipReferalCode = true;
        setUserInfo()
    }

    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Exception$e")
        }
    }

    @get:Bindable
    var firstName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }


    fun setUserInfo(){


        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));


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
    companion object {

        val TAG = "RefferalMobileViewModel"

    }
}
