package com.faizal.bottomnavigation.viewmodel


import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.utils.EnumValidator
import com.faizal.bottomnavigation.utils.Validator
import com.faizal.bottomnavigation.view.FragmentProfile
import com.faizal.bottomnavigation.view.FragmentRegistration
import com.faizal.bottomnavigation.view.FragmentVerification
import com.google.firebase.auth.FirebaseAuth


class RegistrationModel(internal val activity: FragmentActivity, internal val fragmentSignin: FragmentRegistration)// To show list of user images (Gallery)
    :  BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    var TAG = "RegistrationModel"
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
    private var isInternetConnected: Boolean = false

    init {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            // launchChildFragment(FragmentHomePage())
        }
        networkHandler()
    }

    fun signInUserClicked() {
        if (validateInput())
            signInUser(dataUsername, dataPassword)
        else
            showToast(R.string.loginValidtionErrorMsg)
    }


    private fun launchChildFragment(mapFragment: BaseFragment) {
        val bundle = Bundle()
        mapFragment.arguments = bundle
        fragmentSignin.newInstance(1, mapFragment, bundle)
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
        networkStateHandler!!.registerNetWorkStateBroadCast(activity.applicationContext)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(activity.applicationContext)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun signInUser(email: String?, password: String?) {

        if (isInternetConnected) {
            showToast(R.string.network_ErrorMsg)
        } else {

            mAuth.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(activity) { task ->
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        Log.d(TAG,"Exception success "+task.isSuccessful)

                        if (!task.isSuccessful) {
                            // there was an error
                            Log.d(TAG,"Exception success"+task.isSuccessful)
                            showToast(R.string.loginFailed)
                        } else {
                            Log.d(TAG,"Exception success "+task.isSuccessful)
                            showToast(R.string.loginSucess)

                            //      launchChildFragment(FragmentHomePage())
                            sendVerificationEmail()
                        }
                    }.addOnFailureListener {
                        Log.d(TAG,"Exception ->"+it.message)
                        showToast(R.string.loginFailed)
                    }
        }


    }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // email sent

                        val fragment = FragmentVerification()
                        val bundle = Bundle()
                        fragment.setArguments(bundle)
                        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));


                    } else {

                    }
                }
    }

    private fun showToast(id: Int) {
        Toast.makeText(activity.applicationContext, activity.applicationContext.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
