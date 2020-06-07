package com.guiado.grads.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model2.Feedback
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.util.getUserName
import com.guiado.grads.utils.EnumFeedBack
import com.guiado.grads.view.FragmentDiscussions
import com.guiado.grads.view.FragmentFeedBack
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.network.FirbaseWriteHandlerActivity
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
        listOfCoachings = c.readCourseCategory(context)

    }


    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
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

            val firbaseWriteHandler = FirbaseWriteHandlerActivity(fragmentSignin).updateFeedback(feedBack, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                    Toast.makeText(context, context.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                    showProgresss(false)
                }

                override fun onSuccess() {
                    Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                    showProgresss(false)
//                    val fragment = FragmentDiscussions()
//                    val bundle = Bundle()
//                    fragment.setArguments(bundle)
//                    fragmentSignin.mFragmentNavigation.popFragment(1);
//
//                    val intent = Intent(fragmentSignin, )
                    fragmentSignin.finish()

                }
            })

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
