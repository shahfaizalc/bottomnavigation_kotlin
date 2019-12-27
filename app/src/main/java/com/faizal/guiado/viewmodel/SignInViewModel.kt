package com.faizal.guiado.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.guiado.BR
import com.faizal.guiado.R
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.handler.NetworkChangeHandler
import com.faizal.guiado.utils.EnumValidator
import com.faizal.guiado.utils.Validator
import com.faizal.guiado.view.FragmentForgotPassword
import com.faizal.guiado.view.FragmentProfile
import com.faizal.guiado.view.FragmentSignin
import com.faizal.guiado.view.FragmentVerification
import com.google.firebase.auth.FirebaseAuth

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
    private var isInternetConnected: Boolean = false

    init {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            // launchChildFragment(FragmentHomePage())
        }
        networkHandler()
    }

    fun forgotPaswwordFragment() {
        val fragment = FragmentForgotPassword()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(0,fragment,bundle));
    }

    fun doSignInUser() {
        if (validateInput())
            doSignInUser(dataUsername, dataPassword)
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

            mAuth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(context as FragmentActivity) { task ->
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

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
                        Log.d("TAG", "c" + it.message)
                        showToast(R.string.loginFailed)
                    }
        }
    }

    fun isuserVerified(){
        val fragment = FragmentVerification()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0,fragment,bundle));
    }


    private fun launchProfile() {
        val fragment = FragmentProfile()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));
      //  fragmentSignin.mFragmentNavigation.switchTab(0)
        fragmentSignin.mFragmentNavigation.viewBottom(View.VISIBLE)
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
