package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.BonusTopicsResultListener
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.model2.BonusTopics
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*

class ReelDailyBonusMobileViewModel(private val context: Context, private val fragmentSignin: FragmentReelDailyBonus)
    : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var headline1_Points = "";
    var headline1_title = "";
    var headline2_Points = "";
    var headline2_title = "";


    init {
        networkHandler()
        getUserInfo()

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(fragmentSignin) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                        .setTestDeviceIds(listOf("74E96DD154EF70EBE92C022E3505DE45"))
                        .build()
        )


        loadData()
    }

    var dailyBonusTopics = ArrayList<BonusTopics>();
    var totalItems = 0
    var startItem = 0;
    var endItem = 1


    private fun loadData() {
        val firbaseWriteHandlerActivity = FirbaseWriteHandlerActivity(fragmentSignin)
        firbaseWriteHandlerActivity.doGetBonusTopics(object : BonusTopicsResultListener {

            override fun onSuccess(url: List<BonusTopics>) {
                Log.d("TAG", "Success bonus topics size " + url.size)
                dailyBonusTopics.addAll(url);
                totalItems = url.size
                when {
                    url.isEmpty() -> {
                        startItem = -1
                        endItem = -1
                        return
                    }
                    dailyBonusTopics.size > 1 -> {
                        loadBothItems(0, 1)
                    }
                    dailyBonusTopics.size == 1 -> {
                        loadSingleItem(0)
                    }
                }
            }


            override fun onFailure(e: Exception) {
                Log.d("TAG", "Failure bonus topics size " + e.message)

            }
        })
    }

    fun loadSingleItem(item: Int) {
        headline1Points = dailyBonusTopics[item].points.toString();
        headline1title = dailyBonusTopics[item].topicDescription
        headline1_Points = dailyBonusTopics[item].points.toString();
        headline1_title = dailyBonusTopics[item].topicDescription

        startItem = item
        endItem = item
    }

    fun loadBothItems(item1: Int, item2: Int) {
        headline1Points = dailyBonusTopics[item1].points.toString();
        headline1title = dailyBonusTopics[item1].topicDescription
        headline2Points = dailyBonusTopics[item2].points.toString()
        headline2title = dailyBonusTopics[item2].topicDescription
        headline1_Points = dailyBonusTopics[item1].points.toString();
        headline1_title = dailyBonusTopics[item1].topicDescription
        headline2_Points = dailyBonusTopics[item2].points.toString()
        headline2_title = dailyBonusTopics[item2].topicDescription

        startItem = item1
        endItem = item2
    }

    public fun signInUserClicked() {
        //  fragmentSignin.finish()
        // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDailyBonusReels::class.java));

    }


    public fun signUpUserClicked() {
    }


    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipReferalCode = true;

        //  setUserInfo()
    }


    lateinit var userDetails: UserModel
    private var isEdit = false

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Exception$e")
        }
    }

    @get:Bindable
    var firstName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }


    fun setUserInfo() {

        progressBarVisible = View.VISIBLE

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()
        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                if (isEdit) {
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else {
                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));
                }

                Log.d("Authenticaiton token", "onSuccess")
                //   Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                progressBarVisible = View.INVISIBLE
                Log.d("Authenticaiton token", "Exception$e")
                Toast.makeText(context, "Failed to update your profile.. please try again later", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    @get:Bindable
    var headline1Points = headline1_Points
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.headline1Points)
        }

    @get:Bindable
    var headline2Points = headline2_Points
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.headline2Points)
        }

    @get:Bindable
    var headline1title = headline1_title
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.headline1title)
        }

    @get:Bindable
    var headline2title = headline2_title
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.headline2title)
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
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
    }


    companion object {

        val TAG = "ReelTypeMobileViewModel"

    }
}
