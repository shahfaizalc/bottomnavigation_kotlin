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
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.network.FirbaseReadHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.notNull
import com.faizal.bottomnavigation.util.storeUserName
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentProfile
import com.faizal.bottomnavigation.view.FragmentProfileEdit
import com.faizal.bottomnavigation.view.FragmentWelcome
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class ProfileViewModel(private val context: Context, private val fragmentSignin: FragmentProfile) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    private val mAuth: FirebaseAuth
    var profile = Profile()


    init {
        networkHandler()
        mAuth = FirebaseAuth.getInstance()

        readAutoFillItems()
        FirbaseReadHandler().getCurrentUserInfo(object : UseInfoGeneralResultListener {

            override fun onSuccess(profile1: Profile) {
                profile = profile1
                userName = profile1.name ?: ""
                storeUserName(context,mAuth.currentUser?.uid!!,profile)
                userEmail = profile1.email?: ""
                userTitle = profile1.phone?: ""
                userDesc = profile1.desc?: ""
                userMoreInfo = profile1.moreInformation?: ""
                userAvailability = profile1.availability
                userAddress = getAddress()
                keywords = getKeyWords(profile1.keyWords)
            }

            override fun onFailure(e: Exception) {
            }
        })
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

    private fun getKeyWords(keyWords: MutableList<Int>?): String {

        var result = ""

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    result += " " + listOfCoachings!!.get(value - 1).categoryname +", "
                }
            }
        }
        return result;
    }

    private fun getAddress() = " " + profile.address.let { " " + (it?.locationname ?:  "") + "\n " + (it?.streetName
            ?:  "") + "\n " + (it?.town ?:  "") + "\n " + (it?.city ?: "") }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }


    @get:Bindable
    var userAvailability: Boolean = profile.availability
        set(price) {
            field = price
            profile.availability = price
            notifyPropertyChanged(BR.userAvailability)

        }

    @get:Bindable
    var userName: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userName)

        }

    @get:Bindable
    var userEmail: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userEmail)

        }


    @get:Bindable
    var userTitle: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userTitle)

        }

    @get:Bindable
    var userPhone: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userPhone)

        }

    @get:Bindable
    var userDesc: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userDesc)

        }

    @get:Bindable
    var userAddress: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    @get:Bindable
    var userMoreInfo: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userMoreInfo)

        }

    @get:Bindable
    var keywords: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.keywords)

        }

    fun findClickded() {
        Log.d("tag", "taggg")
        val fragment = FragmentProfileEdit()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut();
        val fragment = FragmentWelcome()
        Log.d("tag", "logout")
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0,fragment,bundle));
        fragmentSignin.mFragmentNavigation.viewBottom(View.GONE)
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
}
