package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.view.*

class OccupationViewModel(private val context: Context, private val fragmentSignin: FragmentOccupation) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var posititonSelected = ""

    init {
        networkHandler()
        getUserInfo()

    }

    fun signInUserClicked() {
       // fragmentSignin.finish()
        if(!posititonSelected.isNullOrEmpty()) {
            userDetails.occupation = posititonSelected
            userDetails.skipOccupation = false
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, RelegionActivity::class.java));
        }
    }


    fun signUpUserClicked() {
        // fragmentSignin.finish()
        if(!posititonSelected.isNullOrEmpty()) {
            userDetails.occupation = posititonSelected
            userDetails.skipOccupation = false
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, RelegionActivity::class.java));
        }
    }

    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipOccupation = true
        fragmentSignin.startActivity(Intent(fragmentSignin, RelegionActivity::class.java));

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


    fun prepareData(): ArrayList<String> {

        val values = GenericValues().getFileString("Occupations.json", fragmentSignin)
        val occupations = GenericValues().getOccupationsList(values, fragmentSignin)[0].chapters!!

        val occupationList = ArrayList<String>()
        for(i in occupations){
            occupationList.add(i.occupations)
        }
        return occupationList;
    }

    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    private fun setUserInfo(){

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()
    }
}
