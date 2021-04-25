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
import com.reelme.app.activities.Main2Activity
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*

class HobbiesViewModel(private val context: Context, private val fragmentSignin: FragmentHobbies) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var posititonSelected = ""

    init {
        networkHandler()
        getUserInfo()

    }


    fun signInUserClicked() {
        // fragmentSignin.finish()
        if(!posititonSelected.isEmpty()) {
            userDetails.hobbiesAndInterest = posititonSelected
            setUserInfo()
        }
    }

    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipHobbiesAndInterest = true
        fragmentSignin.startActivity(Intent(fragmentSignin, Main2Activity::class.java));

    }

    fun signUpUserClicked() {
        // fragmentSignin.finish()
        if(!posititonSelected.isNullOrEmpty()) {
            userDetails.hobbiesAndInterest = posititonSelected
            setUserInfo()

        }
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

    fun prepareData(): ArrayList<String> {

        val values = GenericValues().getFileString("hobbies.json", fragmentSignin)
        val occupations = GenericValues().getHobbyList(values, fragmentSignin)[0].chapters!!

        val occupationList = ArrayList<String>()
        for(i in occupations){
            occupationList.add(i.hobbies)
        }
        return occupationList;
    }

    lateinit var userDetails : UserModel
    private  var isEdit = false;


    @get:Bindable
    var hintTextOccupation = if (userDetails.hobbiesAndInterest.isNullOrEmpty()) "Pick your hobbies & interests" else  userDetails.hobbiesAndInterest
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.hintTextOccupation)
        }



    @get:Bindable
    var skipVisibility: Int? =  if (isEdit) View.GONE else  View.VISIBLE
        set(price) {
            field = price
            notifyPropertyChanged(BR.skipVisibility)
        }



    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        if(isEdit){
            skipVisibility = View.GONE
        }

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
            if(!isEdit && !userDetails.username.isNullOrEmpty()){
                fragmentSignin.startActivity(Intent(fragmentSignin, Main2Activity::class.java))
            }
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    private fun setUserInfo(){

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()

        Toast.makeText(context, "Please wait... we are saving your data", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                if (isEdit) {
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else {
                    fragmentSignin.startActivity(Intent(fragmentSignin, Main2Activity::class.java));
                }

                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception" + e)
                Toast.makeText(context, "Failed to save your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }
}
