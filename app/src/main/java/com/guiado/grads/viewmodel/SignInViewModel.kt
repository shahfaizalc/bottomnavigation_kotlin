package com.guiado.grads.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.facebook.FacebookSdk.getApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.Authenticaiton
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.activities.Main2Activity
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.utils.EnumValidator
import com.guiado.grads.utils.Validator
import com.guiado.grads.view.FragmentForgotPassword
import com.guiado.grads.view.FragmentSignin
import com.guiado.grads.view.FragmentVerification
import com.news.list.communication.GetServiceNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
    lateinit var postsService : GetServiceNews
    private fun doSignInUser(email: String?, password: String?) {

        if (isInternetConnected) {
            showToast(R.string.network_ErrorMsg)
        } else {

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://test.salesforce.com/services/oauth2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            postsService = retrofit.create(GetServiceNews::class.java)
            sendPost()


        }
    }

    private fun sendPost() {
        val post = Authenticaiton()
        val call: Call<Authenticaiton?>? = postsService.sendPosts(post)
        call!!.enqueue(object : Callback<Authenticaiton?> {
            override fun onResponse(call: Call<Authenticaiton?>?, response: Response<Authenticaiton?>) {
                response.body().toString()
                var s : Authenticaiton? = response.body();
                Log.d("rach",s!!.accessToken);
            }

            override fun onFailure(call: Call<Authenticaiton?>?, t: Throwable) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show()
            }
        })
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
