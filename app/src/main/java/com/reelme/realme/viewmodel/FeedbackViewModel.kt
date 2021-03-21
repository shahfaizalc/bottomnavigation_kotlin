package com.reelme.realme.viewmodel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.reelme.realme.BR
import com.reelme.realme.R
import com.reelme.realme.handler.NetworkChangeHandler
import com.reelme.realme.listeners.MultipleClickListener
import com.reelme.realme.model2.Feedback
import com.reelme.realme.util.GenericValues
import com.reelme.realme.util.MultipleClickHandler
import com.reelme.realme.util.getUserName
import com.reelme.realme.utils.EnumFeedBack
import com.reelme.realme.view.FragmentFeedBack
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class FeedbackViewModel(private val context: Context, private val fragmentSignin: FragmentFeedBack) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false


    init {
        networkHandler()
        readAutoFillItems()
    }

    private fun readAutoFillItems() {
        val c = GenericValues()

    }




    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)
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

    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }


    fun datePickerClick() = View.OnClickListener {


        if (!handleMultipleClicks()) {

            val feedBack = Feedback();

            if (userEmail.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin, fragmentSignin!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            showProgresss(true)

            feedBack.feedback = userEmail!!
            feedBack.feedbackBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            feedBack.feedbackOn = System.currentTimeMillis().toString()
            feedBack.feedbackUsername = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
            feedBack.feebackStatus = EnumFeedBack.NEW



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
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}
