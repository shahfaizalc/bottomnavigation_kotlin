package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.listeners.MultipleClickListener
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentAddress
import com.faizal.bottomnavigation.view.FragmentKeyWords
import com.faizal.bottomnavigation.view.FragmentProfile
import com.faizal.bottomnavigation.view.FragmentProfileEdit
import com.google.firebase.auth.FirebaseAuth
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
    var userAvailability: Boolean = profile.availability
        set(price) {
            field = price
            profile.availability = price
            notifyPropertyChanged(BR.userAvailability)

        }


    @get:Bindable
    var userName: String? = profile.name
        set(price) {
            field = price
            profile.name = price
            notifyPropertyChanged(BR.userName)

        }

    @get:Bindable
    var userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
        set(price) {
            field = price
            profile.email = price
            notifyPropertyChanged(BR.userEmail)

        }

    @get:Bindable
    var userTitle: String? = profile.title
        set(price) {
            field = price
            profile.title = price
            notifyPropertyChanged(BR.userTitle)

        }

    @get:Bindable
    var userPhone: String? = profile.phone
        set(price) {
            field = price
            profile.phone = price
            notifyPropertyChanged(BR.userPhone)

        }

    @get:Bindable
    var userDesc: String? = profile.desc
        set(price) {
            field = price
            profile.desc = price
            notifyPropertyChanged(BR.userDesc)

        }

    @get:Bindable
    var userAddress: String? = getAddress()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    @get:Bindable
    var userMoreInfo: String? = profile.moreInformation
        set(price) {
            field = price
            profile.moreInformation = price
            notifyPropertyChanged(BR.userMoreInfo)

        }

    @get:Bindable
    var keys: String? = getKeyWords()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keys)

        }

    fun datePickerClick() = View.OnClickListener {


        if (!handleMultipleClicks()) {

            if (profile.name != "" && profile.email != "" && profile.phone != "" && profile.title != "") {
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updateUserInfo(profile, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess ")
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
