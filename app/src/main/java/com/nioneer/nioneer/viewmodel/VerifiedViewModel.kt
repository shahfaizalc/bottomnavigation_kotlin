package com.nioneer.nioneer.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.nioneer.nioneer.R
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.handler.NetworkChangeHandler
import com.nioneer.nioneer.view.*
import com.google.firebase.auth.FirebaseAuth
import com.nioneer.nioneer.activities.Main2Activity

class VerifiedViewModel( private val fragmentSignin: FragmentVerification) :
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
                Toast.makeText(fragmentSignin,fragmentSignin.resources.getString(R.string.USR_DLS_Verify_Email_Title_Txt),Toast.LENGTH_LONG).show()
        }
    }

    fun resendMail() {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.sendEmailVerification()
        Toast.makeText(fragmentSignin,fragmentSignin.resources.getString(R.string.resnd_Verify_Email_Title_Txt),Toast.LENGTH_LONG).show()

      //  fragmentSignin.mFragmentNavigation.switchTab(1);
    }

    fun isuserVerified(){
        Toast.makeText(fragmentSignin,"Verify your email",Toast.LENGTH_LONG).show()
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
//        val fragment = FragmentProfile()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));
//        fragmentSignin.mFragmentNavigation.switchTab(0)
//        fragmentSignin.mFragmentNavigation.viewBottom(View.VISIBLE)

        val intent = Intent(fragmentSignin, Main2Activity::class.java)
        fragmentSignin.startActivity(intent)
    }

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(fragmentSignin)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(fragmentSignin)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(id), Toast.LENGTH_LONG).show()
    }
}
