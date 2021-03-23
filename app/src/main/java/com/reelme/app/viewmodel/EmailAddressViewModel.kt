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
import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailAddressViewModel(private val context: Context, private val fragmentSignin: FragmentEmailAddress) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()
        if(isValidEmail()){
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentFullNameMobile::class.java));
        }
    }

    private fun isValidEmail(): Boolean {

        val regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"

        val pattern: Pattern = Pattern.compile(regex)

        val matcher: Matcher = pattern.matcher(userEmail)
        println(userEmail + " : " + matcher.matches())

        return if(matcher.matches()){
            true;
        } else{
            showToast(R.string.invalid_email_ErrorMsg)
            false
        }
    }


    fun signUpUserClicked() {
       // fragmentSignin.finish()
        if(isValidEmail()){
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentFullNameMobile::class.java));
        }
    }



    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d(RefferalMobileViewModel.TAG, "Exception$e")
        }
    }


    fun setUserInfo(){
        userDetails.emailId = userEmail

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

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
    var userEmail: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)
        }



}
