package com.guiado.linkify.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.linkify.BR
import com.guiado.linkify.Events.MyCustomEvent
import com.guiado.linkify.R
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.listeners.EmptyResultListener
import com.guiado.linkify.listeners.MultipleClickListener
import com.guiado.linkify.model.CoachItem
import com.guiado.linkify.model2.Profile
import com.guiado.linkify.network.FirbaseWriteHandler
import com.guiado.linkify.util.GenericValues
import com.guiado.linkify.util.MultipleClickHandler
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.view.FragmentAddress
import com.guiado.linkify.view.FragmentKeyWords
import com.guiado.linkify.view.FragmentProfile
import com.guiado.linkify.view.FragmentProfileEdit
import com.google.firebase.auth.FirebaseAuth
import com.guiado.linkify.util.storeUserName
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList


class ProfileEditViewModel(private val context: Context, private val fragmentSignin: FragmentProfileEdit, postAdObj: String) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var profile = Profile();

    init {
        networkHandler()
        profile = (GenericValues().getProfile(postAdObj, fragmentSignin.context!!))
        readAutoFillItems()
    }

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.readCourseCategory(context)

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
    /*
   Method will act as the event handler for MyCustomEvent.kt
   */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
        userAddress = getAddress()
        keys = getKeyWords()
    }

    private fun getAddress() = " " + profile.address?.locationname + "\n " + profile.address?.streetName +
            ", " + profile.address?.town + "\n " + profile.address?.city

    private fun getKeyWords(): String {

        var result = ""

        val numbersIterator = profile.keyWords?.iterator()
        numbersIterator?.let {
            while (numbersIterator.hasNext()) {
                var value = (numbersIterator.next())
                result += "" + listOfCoachings!!.get(value - 1).categoryname +", "
            }
        }

        return result;
    }


    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
        set(price) {
            field = price
            profile.email = price
            notifyPropertyChanged(BR.userEmail)

        }


    @get:Bindable
    var userAddress: String? = getAddress()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    @get:Bindable
    var keys: String? = getKeyWords()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keys)

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

            if (profile.name != "" && profile.email != "" && profile.phone != "" && profile.title != "") {
                showProgresss(true)
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updateUserInfo(profile, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                        showProgresss(false)

                    }

                    override fun onSuccess() {
                        showProgresss(false)
                        Log.d(TAG, "DocumentSnapshot onSuccess ")
                        storeUserName(context, FirebaseAuth.getInstance().currentUser!!.uid, profile)

                        val fragment = FragmentProfile()
                        val bundle = Bundle()
                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
                        fragment.setArguments(bundle)
                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            } else {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun updateAddress() = View.OnClickListener {
        EventBus.getDefault().register(this);
        val fragment = FragmentAddress()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }

    fun updateKeyWords() = View.OnClickListener {
        EventBus.getDefault().register(this);
        val fragment = FragmentKeyWords()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
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
