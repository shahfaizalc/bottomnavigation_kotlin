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
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*

class BioViewModel(private val context: Context, private val fragmentSignin: FragmentBioMobile) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
        //  fragmentSignin.finish()

        if(!bio.isNullOrEmpty()) {
            userDetails.skipBio = false
            userDetails.bio = bio
            setUserInfo()
        }
    }


    fun signUpUserClicked() {
        // fragmentSignin.finish()

        if(!bio.isNullOrEmpty()) {
            userDetails.skipBio = false
            userDetails.bio = bio
            setUserInfo()
        }
    }


    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipBio = true
        setUserInfo()
    }



    lateinit var userDetails : UserModel
    private var isEdit = false;

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT",false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    fun setUserInfo(){

        progressBarVisible = View.VISIBLE

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.putBoolean("IS_EDIT",false)
        editor.apply()
        Toast.makeText(context, "Please wait... we are saving your data", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                if(isEdit){
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else{
                    fragmentSignin.startActivity(Intent(fragmentSignin, GenderActivity::class.java));
                }

                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
               // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception"+e)
                Toast.makeText(context, "Failed to save your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }


    @get:Bindable
    var bio: String? = userDetails.bio
        set(price) {
            userNameLength = (price!!.length.toString())+"/80"
            field = price
            notifyPropertyChanged(BR.bio)
        }

    @get:Bindable
    var userNameLength: String? = (bio!!.length.toString())+"/80"
        set(price) {
            field = price
            notifyPropertyChanged(BR.userNameLength)
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
}
