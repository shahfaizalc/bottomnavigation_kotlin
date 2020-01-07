package com.guiado.linkify.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.linkify.BR
import com.guiado.linkify.R
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.listeners.EmptyResultListener
import com.guiado.linkify.listeners.MultipleClickListener
import com.guiado.linkify.model.CoachItem
import com.guiado.linkify.model2.Feedback
import com.guiado.linkify.network.FirbaseWriteHandler
import com.guiado.linkify.util.GenericValues
import com.guiado.linkify.util.MultipleClickHandler
import com.guiado.linkify.util.getUserName
import com.guiado.linkify.utils.EnumFeedBack
import com.guiado.linkify.view.FragmentDiscussions
import com.google.firebase.auth.FirebaseAuth
import com.guiado.linkify.view.FragmentPrivacy
import java.util.*


class PrivacyViewModel(private val context: Context, private val fragmentSignin: FragmentPrivacy) :
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


    fun datePickerClick() = View.OnClickListener {


        if (!handleMultipleClicks()) {

            val feedBack = Feedback();

            if (userEmail.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            feedBack.feedback = userEmail!!
            feedBack.feedbackBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            feedBack.feedbackOn = System.currentTimeMillis().toString()
            feedBack.feedbackUsername = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
            feedBack.feebackStatus = EnumFeedBack.NEW

            val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updateFeedback(feedBack, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                    Toast.makeText(context, context.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess() {
                    Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                    val fragment = FragmentDiscussions()
                    val bundle = Bundle()
                    fragment.setArguments(bundle)
                    fragmentSignin.mFragmentNavigation.popFragment(1);

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
