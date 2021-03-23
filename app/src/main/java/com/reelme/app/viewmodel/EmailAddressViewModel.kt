package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
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

        val matcher: Matcher = pattern.matcher(ideaTitle)
        println(ideaTitle + " : " + matcher.matches())

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

    fun setUserInfo(){

        val userInfo = UserModel();
        userInfo.emailId = ideaTitle

        val gsonValue = Gson().toJson(userInfo)

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
    var ideaTitle: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }



}