package com.reelme.realme.viewmodel

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.reelme.realme.BR
import com.reelme.realme.R
import com.reelme.realme.handler.NetworkChangeHandler
import com.reelme.realme.model2.Profile
import com.reelme.realme.util.GenericValues
import com.reelme.realme.util.MultipleClickHandler
import com.reelme.realme.view.*


class ProfileViewModel(internal val activity:
                       FragmentActivity, private val context: Context, private val fragmentSignin: FragmentProfile) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    private val mAuth: FirebaseAuth
    var profile = Profile()


    init {
        networkHandler()
        mAuth = FirebaseAuth.getInstance()

        readAutoFillItems()
        fragmentSignin.mFragmentNavigation.viewToolbar(true);

    }

    private fun readAutoFillItems() {
        val c = GenericValues()
    }




    private fun getAddress() = " " + profile.address.let { " " + (it?.locationname ?:  "") + "\n " + (it?.streetName
            ?:  "") + ", " + (it?.town ?:  "") + "\n " + (it?.city ?: "") }


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
    var followers: Int = if (profile.followers!=null) profile.followers!!.size else 0
        set(price) {
            field = price
            notifyPropertyChanged(BR.followers)

        }

    @get:Bindable
    var followings: Int =  if (profile.following!=null) profile.following!!.size else 0
        set(price) {
            field = price
            notifyPropertyChanged(BR.followings)

        }

    @get:Bindable
    var userName: String = "balasubramanian panneerselvan "
        set(price) {
            field = price
            notifyPropertyChanged(BR.userName)

        }

    @get:Bindable
    var userEmail: String = "balasubramanian.panneerselvan@philips.com.incpoc"
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
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")

        }
    }




    fun myDiscussionsClicked() {
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")

        }
    }

    fun myEventsClicked() {
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")

        }
    }

    fun logout() {
        if (!handleMultipleClicks()) {
            FirebaseAuth.getInstance().signOut();
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();

            activity.finish();
            var intent = Intent(activity, FragmentWelcome::class.java);
            activity.startActivity(intent);

        }
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

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun privacyClicked() {
        if (!handleMultipleClicks()) {

            val intent = Intent(activity, FragmentPrivacy::class.java);
            activity.startActivity(intent);

        }
    }
    fun aboutClicked() {
        if (!handleMultipleClicks()) {

        }
    }


    fun feedback() {
        Log.d("tag", "taggg")

        val intent = Intent(activity, FragmentFeedBack::class.java);
        activity.startActivity(intent);

    }

    fun savedDiscussionsClicked() {
        if (!handleMultipleClicks()) {

        }
    }

    fun savedEventsClicked() {
        if (!handleMultipleClicks()) {

        }
    }


}
