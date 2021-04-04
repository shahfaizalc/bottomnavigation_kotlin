package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.activities.LaunchActivity
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.model2.Profile
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*


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
            setUserInfo();
            activity.finish();

            val intent = Intent(activity, FragmentWelcome::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent);

        }
    }



    private fun setUserInfo(){
        var userModel = UserModel()

        val gsonValue = Gson().toJson(userModel)
        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()


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
