package com.guiado.grads.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.UseInfoGeneralResultListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model2.Profile
import com.guiado.grads.network.FirbaseReadHandler
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.util.notNull
import com.guiado.grads.util.storeUserName
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.*
import java.util.*


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
//        FirbaseReadHandler().getCurrentUserInfo(object : UseInfoGeneralResultListener {
//
//            override fun onSuccess(profile1: Profile) {
//                profile = profile1
//                userName = profile1.name ?: ""
//                storeUserName(context,mAuth.currentUser?.uid!!,profile)
//                userEmail = mAuth.currentUser?.email ?: ""
//                userTitle = profile1.title?: ""
//                userPhone = profile1.phone?: ""
//                userDesc = profile1.desc?: ""
//                userMoreInfo = profile1.moreInformation?: ""
//                userAvailability = profile1.availability
//                userAddress = getAddress()
//                keywords = getKeyWords(profile1.keyWords)
//                followers = profile1.following?.size ?: 0
//                followings = profile1.followers?.size ?: 0
//            }
//
//            override fun onFailure(e: Exception) {
//            }
//        })
        fragmentSignin.mFragmentNavigation.viewToolbar(true);

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
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")
//            val fragment = FragmentProfileEdit()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
//
          val intent = Intent(fragmentSignin.activity, FragmentProfileEdit::class.java)
            intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
            fragmentSignin.activity!!.startActivity(intent)

        }
    }

//    fun feedback() {
//        Log.d("tag", "taggg")
//        val fragment = FragmentFeedBack()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
//    }

    fun info() {
        Log.d("tag", "taggg")
//        val fragment = FragmentInfo()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));


        val intent = Intent(fragmentSignin.activity, FragmentInfo::class.java);
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragmentSignin.activity!!.startActivity(intent)

    }
    fun settings() {
        Log.d("tag", "taggg")
        val fragment = FragmentSettings()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }

    fun myDiscussionsClicked() {
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")
//            val fragment = FragmentMyDiscussions()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));

            val intent = Intent(fragmentSignin.activity, FragmentMyDiscussions::class.java)
            intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
            fragmentSignin.activity!!.startActivity(intent);

        }
    }

    fun myEventsClicked() {
        if (!handleMultipleClicks()) {
            Log.d("tag", "taggg")
//            val fragment = FragmentMyEvents()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));

//
//            val intent = Intent(activity, FragmentMyEvents::class.java);
//            intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//            activity.startActivity(intent);

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

    fun inviteFriends(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = context.resources.getString(R.string.shareInfo)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"))

    }
    fun privacyClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentPrivacy()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
//

            val intent = Intent(activity, FragmentPrivacy::class.java);
            activity.startActivity(intent);

        }
    }
    fun aboutClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentAbout()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
//

            val intent = Intent(activity, FragmentAbout::class.java);
            activity.startActivity(intent);

        }
    }


    fun feedback() {
        Log.d("tag", "taggg")
//        val fragment = FragmentFeedBack()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));

        val intent = Intent(activity, FragmentFeedBack::class.java);
        activity.startActivity(intent);

    }

    fun savedDiscussionsClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentSavedDiscussions()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
//
//
            val intent = Intent(activity, FragmentSavedDiscussions::class.java);
            activity.startActivity(intent);

        }
    }

    fun savedEventsClicked() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentSavedEvents()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1,fragment,bundle));
//
//            val intent = Intent(activity, FragmentSavedEvents::class.java);
//            activity.startActivity(intent);
        }
    }


}
