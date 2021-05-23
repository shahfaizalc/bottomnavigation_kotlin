package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.activities.Main2Activity
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


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

    private fun getPhoneNumberUI(): String? {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        return getFormattedNumber(sharedPreference.getString("phoneNumber", "")!!)
    }

    private fun getFormattedNumber(phoneNumber: String): String? {
        var phoneNumber: String? = phoneNumber
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        val numberFormat = Phonemetadata.NumberFormat()
        numberFormat.pattern = "(\\d{3})(\\d{3})(\\d{4})"
        numberFormat.format = "($1) $2-$3"
        val newNumberFormats: MutableList<Phonemetadata.NumberFormat> = ArrayList()
        newNumberFormats.add(numberFormat)
        var phoneNumberPN: PhoneNumber? = null
        try {
            phoneNumberPN = phoneNumberUtil.parse(phoneNumber, Locale.US.getCountry())
            phoneNumber = phoneNumberUtil.formatByPattern(phoneNumberPN, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL, newNumberFormats)
        } catch (e: NumberParseException) {
            e.printStackTrace()
        }
        return phoneNumber
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
           // smsotp = "" + (authCredential.smsCode)
            return "" + (authCredential.smsCode)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Exception")
            return ""
        }
    }



    @get:Bindable
    var ideaTitle: String? ="Text sent to "+ getPhoneNumberUI()
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }


    @get:Bindable
    var smsotp: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.smsotp)
            if(smsotp!!.length == 6){
                Log.d(TAG, "signInWithCredential:smsotp:")
                signUpUserClicked()
            } else{
                Log.d(TAG, "signInWithCredential:smsotp->"+smsotp!!.length)

            }
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

                        Log.d(TAG, "signInWithCredential:success"+Firebase.auth.currentUser)

                        getUserInfo()


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

    private fun loginProcess() {
                                val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
                                val coronaJson = sharedPreference.getString("USER_INFO", "");
                                isEdit = sharedPreference.getBoolean("IS_EDIT", false)

                                try {
                                    val authValue = Gson().fromJson(coronaJson, UserModel::class.java)
                                   // Log.d("Authentication token", auth?.emailId)
                                    userDetails = (authValue as UserModel)
                                    if(null == userDetails){
                                        setUserInfo(getPhoneNumber()!!)

                                    } else if(userDetails.isBlocked!!){
                                        Toast.makeText(fragmentSignin.applicationContext,"Your Account is blocked.\nKindly contact customer care",Toast.LENGTH_LONG).show()

                                    } else if(userDetails.isDeactivated!!){
                                        val builder = AlertDialog.Builder(fragmentSignin)
                                        //set title for alert dialog
                                        builder.setTitle("Deactivated")
                                        //set message for alert dialog
                                        builder.setMessage("Your account is deactivated. Click \'yes\'to activate it again")

                                        //performing positive action
                                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                                            setUserInfo(getPhoneNumber()!!)
                                        }
                                        //performing cancel action
                                        builder.setNeutralButton("Cancel"){dialogInterface , which ->
                                            Toast.makeText(fragmentSignin.applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
                                        }


                                        // Create the AlertDialog
                                        val alertDialog: AlertDialog = builder.create()
                                        // Set other dialog properties
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    } else{
                                        setUserInfo(getPhoneNumber()!!)
                                    }
                                } catch (e: java.lang.Exception) {
                                    Log.d("Authenticaiton token", "Exception"+e.message)
                                    userDetails = UserModel()
                                    setUserInfo(getPhoneNumber()!!)

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

        userDetails.phoneNumber = phoneNumber
        userDetails.isDeactivated = false

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()

        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));
        progressBarVisible = View.INVISIBLE

//        GenericValues().isUserProfileComplete(fragmentSignin, object : UseInfoGeneralResultListener {
//            override fun onSuccess(userInfoGeneral: UserModel) {
//                progressBarVisible = View.INVISIBLE
//            }
//
//            override fun onFailure(e: Exception) {
//                progressBarVisible = View.INVISIBLE
//                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReferralMobile::class.java));
//            }
//        })

    }


    lateinit var userDetails : UserModel
    private  var isEdit = false;

    private fun getUserInfo() {

        GenericValues().preferenceUserProfile(fragmentSignin, object : UseInfoGeneralResultListener{
            override fun onSuccess(userInfoGeneral: UserModel) {
                Log.d(TAG, "Success data fetch")
                loginProcess()
            }

            override fun onFailure(e: Exception) {
                Log.d(TAG, "Exception${e.message}")
                if(e.message.equals("Document not found")) {
                    loginProcess()
                } else{
                    showToast(R.string.errorMsgGeneric)
                    fragmentSignin.finish()
                }


            }
        })
//        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
//        val coronaJson = sharedPreference.getString("USER_INFO", "");
//        isEdit = sharedPreference.getBoolean("IS_EDIT", false)
//
//        try {
//            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
//            Log.d("Authentication token", auth.emailId!!)
//            userDetails = (auth as UserModel)
//        } catch (e: java.lang.Exception) {
//            Log.d("Authenticaiton token", "Exception")
//        }
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
