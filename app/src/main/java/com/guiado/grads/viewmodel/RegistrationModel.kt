package com.guiado.grads.viewmodel


import android.app.Dialog
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.utils.EnumValidator
import com.guiado.grads.utils.Validator
import com.guiado.grads.view.FragmentRegistration
import com.guiado.grads.view.FragmentVerification
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.model2.Profile
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FragmentProfile


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

    @get:Bindable
    var confirmPassword: String? = null
        set(confirmPassword) {
            field = confirmPassword
            notifyPropertyChanged(BR.confirmPassword)
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
    var dataEmail: String? = null
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.dataEmail)
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
        networkHandler()
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun termsAndCondition() {

        if(!handleMultipleClicks()) {

            val dialog = Dialog(activity,android.R.style.Theme_Light_NoTitleBar_Fullscreen)
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_termsandconditions)

            val btndialog: TextView = dialog.findViewById(R.id.btndialog) as TextView
            btndialog.setOnClickListener({ dialog.dismiss() })

            dialog.show()
        }
    }

        fun signInUserClicked() {
        errorTxt = ""

        if (validateInput()){
            if(confirmPassword.equals(dataPassword)){
                signInUser(dataEmail, dataPassword)
            } else {
                showToast(R.string.passwordMismatch)
            }
        }
        else
            showToast(R.string.loginValidtionErrorMsg)
    }


    private fun launchChildFragment(mapFragment: BaseFragment) {
        val bundle = Bundle()
        mapFragment.arguments = bundle
        fragmentSignin.newInstance(1, mapFragment, bundle)
    }

    private fun validateInput(): Boolean {

        if (dataEmail == null || dataPassword == null) {
            return false
        } else if (dataEmail!!.length < 1 || dataPassword!!.length < 1) {
            return false
        } else
            return Validator().validate(dataEmail, EnumValidator.EMAIL_PATTERN)

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

            showProgresss(true)
            mAuth.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(activity) { task ->

                        Log.d(TAG,"Exception success "+task.isSuccessful)

                        if (!task.isSuccessful) {
                            // there was an error
                            Log.d(TAG,"Exception success"+task.isSuccessful)
                            showToast(R.string.creationFailed)
                            showProgresss(false)
                        } else {
                            Log.d(TAG,"Exception success "+task.isSuccessful)

                            //      launchChildFragment(FragmentHomePage())
                            storeUserProfile()
                        }
                    }.addOnFailureListener {
                        showProgresss(false)
                        Log.d(TAG,"Exception ->"+it.message)
                        errorTxt = it.message
                    }
        }

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


   private fun storeUserProfile(){

       val profile = Profile()
       profile.name = dataUsername
       profile.email = dataEmail
       FirbaseWriteHandler(fragmentSignin).updateUserInfo(profile, object : EmptyResultListener {
           override fun onFailure(e: Exception) {
               Log.d(TAG, "onFailure storeUserProfile" + e.message)
               sendVerificationEmail()
           }

           override fun onSuccess() {
               sendVerificationEmail()
           }
       })
   }

    private fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    showProgresss(false)
                    if (task.isSuccessful) { // email sent
                        showToast(R.string.loginSucess)

                        val fragment = FragmentVerification()
                        val bundle = Bundle()
                        fragment.setArguments(bundle)
                        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(1, fragment, bundle));

                    } else {
                        showToast(R.string.creationFailed)
                    }
                }
    }

    private fun showToast(id: Int) {
        errorTxt =  activity.applicationContext.resources.getString(id)
    }
}
