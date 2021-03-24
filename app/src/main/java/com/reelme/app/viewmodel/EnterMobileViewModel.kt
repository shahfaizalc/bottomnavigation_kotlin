package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.view.*
import java.util.concurrent.TimeUnit

class EnterMobileViewModel(private val context: Context, private val fragmentSignin: FragmentEnterMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    init {
        networkHandler()
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        if(!auth.uid.isNullOrEmpty()){
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
        }


        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
              //  signInWithPhoneAuthCredential(credential)

                var gsonValue = Gson().toJson(credential)

                val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("AUTH_INFO",gsonValue)
                editor.apply()

                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentVerifyMobile::class.java));

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                Log.d(TAG, "onCodeSenttoken:$token")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                verifyPhoneNumberWithCode(verificationId,"123123")
            }
        }
        // [END phone_auth_callbacks]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        Log.d(TAG, "credential:${credential.smsCode}")
        // [END verify_with_code]


        val gsonValue = Gson().toJson(credential)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("AUTH_INFO",gsonValue)
        editor.putString("phoneNumber",ideaTitle)
        editor.apply()

        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentVerifyMobile::class.java));

    }


    @get:Bindable
    var ideaTitle: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }



    fun signInUserClicked() {
       // fragmentSignin.finish()
        Log.d(TAG, "signInUserClicked:")

        startPhoneNumberVerification(ideaTitle!!)

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        Log.d(TAG, "signUpUserClicked:phoneNumber"+phoneNumber)
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(fragmentSignin)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    fun signUpUserClicked() {
       // fragmentSignin.finish()
        Log.d(TAG, "signUpUserClicked:")
        startPhoneNumberVerification(ideaTitle!!)

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
