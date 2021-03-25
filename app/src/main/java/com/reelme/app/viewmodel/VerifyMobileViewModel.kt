package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.view.*
import java.util.concurrent.TimeUnit


class VerifyMobileViewModel(private val context: Context, private val fragmentSignin: FragmentVerifyMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    private lateinit var auth: FirebaseAuth

    private  lateinit var authCredential: PhoneAuthCredential
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    init {
        networkHandler()
        auth = Firebase.auth
        Log.d("Authentication auth", "" + auth.currentUser)

            // Initialize phone auth callbacks
            // [START phone_auth_callbacks]
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {


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
                    val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putString("AUTH_INFO", verificationId)
                    editor.apply()
                }
            }
            // [END phone_auth_callbacks]
        }

    private fun getPhoneNumber(): String? {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        return sharedPreference.getString("phoneNumber", "")
    }

    private fun getTokenNumber(): PhoneAuthProvider.ForceResendingToken? {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        return Gson().fromJson(sharedPreference.getString("token", ""), PhoneAuthProvider.ForceResendingToken::class.java)
    }

    private fun getSMS(code: String): String {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");

        try {
            authCredential = PhoneAuthProvider.getCredential(coronaJson, code)
            Log.d(TAG, "sms code " + authCredential.smsCode)
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

                    progressBarVisible = View.INVISIBLE
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
                            Toast.makeText(context, "The verification code entered was invalid", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }
                .addOnFailureListener {
                    progressBarVisible = View.INVISIBLE
                }
    }
    // [END sign_in_with_phone]

    fun signInUserClicked() {
      //  fragmentSignin.finish()
        getSMS(smsotp!!)
        progressBarVisible = View.VISIBLE

        signInWithPhoneAuthCredential(authCredential)
    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
        getSMS(smsotp!!)
        progressBarVisible = View.VISIBLE

        signInWithPhoneAuthCredential(authCredential)
    }


    fun resendClicked() {
        resendVerificationCode(getPhoneNumber()!!, getTokenNumber()!!)
    }


    fun setUserInfo(phoneNumber: String){

        val userModel = UserModel()
        userModel.phoneNumber = phoneNumber

        val gsonValue = Gson().toJson(userModel)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()

        GenericValues().isUserProfileComplete(fragmentSignin, object : UseInfoGeneralResultListener {
            override fun onSuccess(userInfoGeneral: UserModel) {
                progressBarVisible = View.INVISIBLE
            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReferralMobile::class.java));
            }
        })

    }


    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
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

    companion object {
        private const val TAG = "VerifyMobileViewModel"
    }


    private fun resendVerificationCode(
            phoneNumber: String,
            token: PhoneAuthProvider.ForceResendingToken?
    ) {
        progressBarVisible = View.VISIBLE
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(fragmentSignin)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }



}
