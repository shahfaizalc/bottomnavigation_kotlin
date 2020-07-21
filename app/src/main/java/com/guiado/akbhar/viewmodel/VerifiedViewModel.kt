package com.guiado.akbhar.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.akbhar.R
import com.guiado.akbhar.BR
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.view.*
import com.google.firebase.auth.FirebaseAuth

class VerifiedViewModel(private val context: Context, private val fragmentSignin: FragmentVerification) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
    }

    fun accountVerfied() {
      val mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.reload()

        mAuth.currentUser?.run {
            if(mAuth.currentUser?.isEmailVerified!!)
                launchProfile()
            else
                Toast.makeText(context,context.resources.getString(R.string.USR_DLS_Verify_Email_Title_Txt),Toast.LENGTH_LONG).show()
        }
    }

    fun resendMail() {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.sendEmailVerification()
        Toast.makeText(context,context.resources.getString(R.string.resnd_Verify_Email_Title_Txt),Toast.LENGTH_LONG).show()

      //  fragmentSignin.mFragmentNavigation.switchTab(1);
    }

    fun isuserVerified(){
        Toast.makeText(context,"Verify your email",Toast.LENGTH_LONG).show()
//        val fragment = FragmentVerification()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0,fragment,bundle));
    }

    @get:Bindable
    var emailId: String? = FirebaseAuth.getInstance().currentUser!!.email
        set(city) {
            field = city
            notifyPropertyChanged(BR.emailId)
        }

    private fun launchProfile() {
        val fragment = FragmentProfile()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));
        fragmentSignin.mFragmentNavigation.switchTab(0)
        fragmentSignin.mFragmentNavigation.viewBottom(View.VISIBLE)
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
}
