package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.adapter.CommentsAdapter
import com.faizal.bottomnavigation.adapter.RatingsAdapter
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.network.FirbaseReadHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.convertLongToTime
import com.faizal.bottomnavigation.util.getDiscussionKeys
import com.faizal.bottomnavigation.util.notNull
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentOneDiscussion
import com.faizal.bottomnavigation.view.FragmentProfile
import com.faizal.bottomnavigation.view.FragmentProfileEdit
import com.faizal.bottomnavigation.view.FragmentWelcome
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class OneDiscussionViewModel(private val context: Context, private val fragmentSignin: FragmentOneDiscussion,internal val postAdObj: String):
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false



    init {
        networkHandler()

        readAutoFillItems()

    }

    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }
    fun updateReview() = View.OnClickListener {
    }

    var adapter = CommentsAdapter()

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.getDisccussion(postAdObj,context)

    }

    @get:Bindable
    var listOfCoachings: PostDiscussion? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionKeys(listOfCoachings!!.keyWords,context).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }


    @get:Bindable
    var postedDate: String? = listOfCoachings!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.postedDate)

        }


//    fun findClickded() {
//        Log.d("tag", "taggg")
//        val fragment = FragmentProfileEdit()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
//    }
//
//    fun logout() {
//        FirebaseAuth.getInstance().signOut();
//        val fragment = FragmentWelcome()
//        Log.d("tag", "logout")
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0,fragment,bundle));
//        fragmentSignin.mFragmentNavigation.viewBottom(View.GONE)
//    }

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
