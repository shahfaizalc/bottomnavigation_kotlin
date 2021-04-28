package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonemetadata
import com.google.i18n.phonenumbers.Phonenumber
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomeTab::class.java));
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
                progressBarVisible = View.INVISIBLE

                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentVerifyMobile::class.java));


            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                progressBarVisible = View.INVISIBLE

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(context, "Invalid request... kindly check the entered number.", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(context, "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                progressBarVisible = View.INVISIBLE
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                Log.d(TAG, "onCodeSenttoken:$token")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                verifyPhoneNumberWithCode(verificationId,token)
            }
        }
        // [END phone_auth_callbacks]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
        // [START verify_with_code]
//        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
//        Log.d(TAG, "credential:${credential.smsCode}")
        // [END verify_with_code]


        val gsonValue = Gson().toJson(token)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("AUTH_INFO",verificationId)
        editor.putString("phoneNumber",ideaTitle)
        editor.putString("token",gsonValue)
        editor.apply()
        progressBarVisible = View.INVISIBLE

        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentVerifyMobile::class.java));
    }


    @get:Bindable
    var ideaTitle22: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle22)
        }

    @get:Bindable
    var ideaTitle: String? = ""
        set(price) {
            field = price
            getFormattedNumber(price!!)
            notifyPropertyChanged(BR.ideaTitle)
        }

    fun signInUserClicked() {
       // fragmentSignin.finish()
        Log.d(TAG, "signInUserClicked:")
        progressBarVisible = View.VISIBLE

        startPhoneNumberVerification(ideaTitle!!)

    }


    fun signUpUserClicked() {
        // fragmentSignin.finish()
        Log.d(TAG, "signUpUserClicked:")
        progressBarVisible = View.VISIBLE
        startPhoneNumberVerification(ideaTitle!!)

    }
//    private fun textFormat(input : String) {
//
//        val number = input.replace("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
//
//        println(number);
//    }

    private fun getFormattedNumber(phoneNumber: String): String? {
        var phoneNumber: String? = phoneNumber
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val numberFormat = Phonemetadata.NumberFormat()
        numberFormat.pattern = "(\\d{3})(\\d{3})(\\d+)"
        numberFormat.format = "($1) $2-$3"
        val newNumberFormats: MutableList<Phonemetadata.NumberFormat> = ArrayList()
        newNumberFormats.add(numberFormat)
        var phoneNumberPN: Phonenumber.PhoneNumber? = null
        try {
            phoneNumberPN = phoneNumberUtil.parse(phoneNumber, Locale.US.getCountry())
            phoneNumber = phoneNumberUtil.formatByPattern(phoneNumberPN, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL, newNumberFormats)
            Log.d("number buumber","buumber "+phoneNumber)
        } catch (e: NumberParseException) {
            e.printStackTrace()
        }
        ideaTitle22 = phoneNumber

        return phoneNumber
    }


    private fun startPhoneNumberVerification(phoneNumber: String) {
        Log.d(TAG, "signUpUserClicked:phoneNumber" + phoneNumber)
        // [START start_phone_auth]
        if (phoneNumber.isNullOrEmpty()) {

            Toast.makeText(context,"Kindly enter your phone number",Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 50); show()}
            progressBarVisible = View.INVISIBLE

        } else {
            Toast.makeText(context, "Please wait... we are  authenticating your account", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
            val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(fragmentSignin)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            // [END start_phone_auth]
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


    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}
