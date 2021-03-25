package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.pojos.UserModel
import com.reelme.app.view.*

class VerifyMobileViewModel(private val context: Context, private val fragmentSignin: FragmentVerifyMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    private lateinit var auth: FirebaseAuth

    private  lateinit var authCredential: PhoneAuthCredential
    init {
        networkHandler()
        auth = Firebase.auth
        Log.d("Authentication auth", ""+auth.currentUser)
    }

    private fun getPhoneNumber(): String? {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        return sharedPreference.getString("phoneNumber","")
    }

    private fun getSMS(code : String): String {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");

        try {
            authCredential = PhoneAuthProvider.getCredential(coronaJson, code)
            Log.d(TAG, "sms code "+authCredential.smsCode)
            smsotp = "" + (authCredential.smsCode)
            return "" + (authCredential.smsCode)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Exception")
            return ""
        }
    }

    @get:Bindable
    var ideaTitle: String? = getPhoneNumber()
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }


    @get:Bindable
    var smsotp: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.smsotp)
        }



    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(fragmentSignin) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        val user = task.result?.user

                        auth = Firebase.auth
                        setUserInfo(getPhoneNumber()!!)
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(context, "The verification code entered was invalid", Toast.LENGTH_LONG).show()

                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }
    }
    // [END sign_in_with_phone]

    fun signInUserClicked() {
      //  fragmentSignin.finish()
        getSMS(smsotp!!)

        signInWithPhoneAuthCredential(authCredential)

    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
        getSMS(smsotp!!)

        signInWithPhoneAuthCredential(authCredential)
    }


    fun setUserInfo(phoneNumber : String){

        val userModel = UserModel()
        userModel.phoneNumber = phoneNumber

        val gsonValue = Gson().toJson(userModel)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReferralMobile::class.java));
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
        private const val TAG = "VerifyMobileViewModel"
    }

}
