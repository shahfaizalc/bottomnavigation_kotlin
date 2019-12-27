package com.guiado.grads.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.util.notNull
import com.guiado.grads.utils.EnumValidator
import com.guiado.grads.utils.Validator
import com.guiado.grads.view.FragmentForgotPassword
import com.guiado.grads.view.FragmentProfile
import com.guiado.grads.view.FragmentVerification
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordViewModel(private val context: Context, private val fragmentSignin: FragmentForgotPassword) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {
    companion object {
        private val TAG = "ProfileGalleryViewModel"
    }

    private val mAuth: FirebaseAuth
    private var networkStateHandler: NetworkChangeHandler? = null

    @get:Bindable
    var dataUsername: String? = null
        set(username) {
            field = username
            notifyPropertyChanged(BR.dataUsername)
        }

    private var isInternetConnected: Boolean = false

    init {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            // launchChildFragment(FragmentHomePage())
        }
        networkHandler()
    }

    fun sendEmail() {
        if (validateInput())
            signInUser(dataUsername)
        else
            showToast(R.string.loginValidtionErrorMsg)
    }


    private fun launchChildFragment(mapFragment: BaseFragment) {
        val bundle = Bundle()
        mapFragment.arguments = bundle
        fragmentSignin.newInstance(1, mapFragment, bundle)
    }

    private fun validateInput(): Boolean {

        if (dataUsername == null) {
            return false
        }
        return if (dataUsername!!.length < 1) {
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

    private fun signInUser(email: String?) {

        if (isInternetConnected) {
            showToast(R.string.network_ErrorMsg)
        } else {

            email?.notNull {
                mAuth.sendPasswordResetEmail(it)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Email sent.")
                                showToast(R.string.forgotPasswordSuccess)
                            }
                        }.addOnFailureListener {
                            Log.d(TAG, "Exception" + it.message)
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
            }
        }
    }

    fun isuserVerified() {
        val fragment = FragmentVerification()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0, fragment, bundle));
    }


    private fun launchProfile() {
        val fragment = FragmentProfile()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));
        fragmentSignin.mFragmentNavigation.switchTab(0)
        fragmentSignin.mFragmentNavigation.viewBottom(View.VISIBLE)
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
