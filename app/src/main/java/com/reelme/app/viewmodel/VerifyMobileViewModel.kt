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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.view.*

class VerifyMobileViewModel(private val context: Context, private val fragmentSignin: FragmentVerifyMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    private lateinit var auth: FirebaseAuth

    init {
        networkHandler()
        auth = Firebase.auth

        getAccessToken()
    }

    private fun getAccessToken() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");
        val phoneNumber = sharedPreference.getString("phoneNumber","")
        ideaTitle = "Text sent to $phoneNumber";

        try {
            val auth = Gson().fromJson(coronaJson, PhoneAuthCredential::class.java)
            Log.d("Authentication token", auth.smsCode)
            ideaTitle2 = (auth.smsCode)
            signInUserClicked()
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    @get:Bindable
    var ideaTitle: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }


    @get:Bindable
    var ideaTitle2: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle2)
        }


//
//    // [START sign_in_with_phone]
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(fragmentSignin) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithCredential:success")
//
//                        val user = task.result?.user
//                    } else {
//                        // Sign in failed, display a message and update the UI
//                        Log.w(TAG, "signInWithCredential:failure", task.exception)
//                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                            // The verification code entered was invalid
//                        }
//                        // Update UI
//                    }
//                }
//    }
    // [END sign_in_with_phone]

    fun signInUserClicked() {
      //  fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReferralMobile::class.java));
    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
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
        private const val TAG = "PhoneAuthActivity"
    }

}
