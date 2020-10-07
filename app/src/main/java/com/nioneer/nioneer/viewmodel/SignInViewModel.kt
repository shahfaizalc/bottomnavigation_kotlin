package com.nioneer.nioneer.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.R
import com.nioneer.nioneer.handler.NetworkChangeHandler
import com.nioneer.nioneer.utils.EnumValidator
import com.nioneer.nioneer.utils.Validator
import com.nioneer.nioneer.view.FragmentForgotPassword
import com.nioneer.nioneer.view.FragmentSignin
import com.nioneer.nioneer.view.FragmentVerification

import com.google.firebase.auth.FirebaseAuth
import com.nioneer.nioneer.activities.Main2Activity

class SignInViewModel(private val context: Context, private val fragmentSignin: FragmentSignin)
    : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {
    private val mAuth: FirebaseAuth
    private var networkStateHandler: NetworkChangeHandler? = null
    @get:Bindable
    var dataUsername: String? = null
        set(username) {
            field = username
            notifyPropertyChanged(BR.dataUsername)
        }
    @get:Bindable
    var dataPassword: String? = null
        set(password) {
            field = password
            notifyPropertyChanged(BR.dataPassword)
        }

    @get:Bindable
    var showSignUpProgress: Int = View.INVISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpProgress)
        }

    @get:Bindable
    var showSignUpBtn: Int = View.VISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpBtn)
        }

    @get:Bindable
    var errorTxt: String? = null
        set(password) {
            field = password
            notifyPropertyChanged(BR.errorTxt)
        }




    private var isInternetConnected: Boolean = false

    init {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            // launchChildFragment(FragmentHomePage())
        }
        networkHandler()
    }

    fun forgotPaswwordFragment() {
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentForgotPassword::class.java));
    }

    fun doSignInUser() {
        errorTxt = ""

        if (validateInput())
            doSignInUser(dataUsername, dataPassword)
        else
            showToast(R.string.loginValidtionErrorMsg)
    }


    private fun validateInput(): Boolean {

        if (dataUsername == null && dataPassword == null) {
            return false
        }
        return if (dataUsername!!.length < 1 && dataPassword!!.length < 1) {
            false
        } else Validator().validate(dataUsername, EnumValidator.EMAIL_PATTERN)

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

    private fun doSignInUser(email: String?, password: String?) {

        if (isInternetConnected) {
            showToast(R.string.network_ErrorMsg)
        } else {
            showProgresss(true)

            mAuth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(fragmentSignin) { task ->
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        showProgresss(false)
                        Log.d("TAG", "Exception success" + task.isSuccessful)

                        if (!task.isSuccessful) {
                            // there was an error
                            showToast(R.string.loginFailed)
                        } else {
                            showToast(R.string.loginSucess)

                            mAuth.currentUser?.run {
                                if(mAuth.currentUser?.isEmailVerified!!)
                                    launchProfile()
                                else
                                    isuserVerified()
                            }
                        }
                    }.addOnFailureListener {
                        showProgresss(false)
                        Log.d("TAG", "c" + it.message)
                      //  showToast(R.string.loginFailed)
                        errorTxt = it.message
                    }
        }
    }
    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }

    fun isuserVerified(){
        fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentVerification::class.java));
    }


    private fun launchProfile() {
        fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, Main2Activity::class.java));
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
        errorTxt =  context.resources.getString(id)
    }
}
