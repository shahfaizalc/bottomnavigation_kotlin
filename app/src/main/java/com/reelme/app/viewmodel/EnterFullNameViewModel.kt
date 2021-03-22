package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.firebase.auth.PhoneAuthCredential
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.model3.UserDetails
import com.reelme.app.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class EnterFullNameViewModel(private val context: Context, private val fragmentSignin: FragmentFullNameMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()

       if( isValidName(firstName!!) && isValidName(lastName!!)){
           setUserInfo()
           fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDate::class.java));
       }
    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
        if( isValidName(firstName!!) && isValidName(lastName!!)){
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDate::class.java));
        }
    }



    private fun isValidName(name : String): Boolean {

        val regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"

        val pattern: Pattern = Pattern.compile(regex)

        val matcher: Matcher = pattern.matcher(name)
        println(name + " : " + matcher.matches())

        return if(matcher.matches()){
            true;
        } else{
            showToast(R.string.invalid_name_ErrorMsg)
            false
        }
    }

    lateinit var userDetails : UserDetails

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserDetails::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserDetails)
            signInUserClicked()
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    fun setUserInfo(){

        userDetails.firstName = firstName
        userDetails.secondName = lastName

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

    @get:Bindable
    var firstName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.lastName)
        }


    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
