package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.FragmentEmailAddress
import com.reelme.app.view.FragmentFullNameMobile
import com.reelme.app.view.FragmentReferralMobile
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
        if (isValidEmail()) {
            setUserInfo()
        }
    }

    private fun isValidEmail(): Boolean {

        val regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"

        val pattern: Pattern = Pattern.compile(regex)

        val matcher: Matcher = pattern.matcher(userEmail)
        println(userEmail + " : " + matcher.matches())

        return if (matcher.matches()) {
            true;
        } else {
            showToast(R.string.invalid_email_ErrorMsg)
            false
        }
    }


    fun signUpUserClicked() {
        // fragmentSignin.finish()
        if (isValidEmail()) {
            setUserInfo()
        }
    }


    private var isEdit = false;
    lateinit var userDetails: UserModel

    var userEmailId = "";
    private fun getUserInfo() {
        val sharedPreference = fragmentSignin.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId!!)
            userDetails = (auth as UserModel)

//            if (!isEdit && !userDetails.emailId.isNullOrEmpty()) {
//                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentFullNameMobile::class.java))
//            } else if ( isEdit && userDetails.emailId.isNullOrEmpty()){
                userEmailId = userDetails.emailId.toString()
          //  }

        } catch (e: java.lang.Exception) {
            Log.d(RefferalMobileViewModel.TAG, "Exception$e")
        }
    }

    fun setUserInfo() {

        if (!isEdit) {
            if (userDetails.emailId.isNullOrEmpty()) {
                validDataSave()
            } else if (userDetails.emailId == userEmail) {
                GenericValues().navigateToNext(fragmentSignin)
            } else {
                Toast.makeText(fragmentSignin, "Enter valid Email Address", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }
        } else {

            if (userDetails.emailId == userEmail) {
                //Toast.makeText(fragmentSignin, "It's your Email Id", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                fragmentSignin.finish();
            } else {
                validDataSave()
            }
        }

    }

    private fun validDataSave() {
        userDetails.emailId = userEmail

        progressBarVisible = View.VISIBLE

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)

        editor.apply()
        Toast.makeText(context, "Please wait... we are saving your data", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                if (isEdit) {
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else {
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
                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception" + e)
                Toast.makeText(context, "Failed to save your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
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
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
    }

    @get:Bindable
    var userEmail: String? = if(isEdit) userDetails.emailId else ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)
        }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }


}
