package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.network.FirbaseReadHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.*
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel(private val context: Context, private val fragmentSignin: FragmentProfile) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()

        FirbaseReadHandler().getUserInfo(object:UseInfoGeneralResultListener{
            override fun onSuccess(userInfoGeneral: Profile) {

                Log.d("shalini","paney"+userInfoGeneral.address)

            }

            override fun onFailure(e: Exception) {
            }
        })
    }


    var imgUrl =""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun findClickded()
    {
        Log.d("tag","taggg")
        val fragment = FragmentProfileEdit()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
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
