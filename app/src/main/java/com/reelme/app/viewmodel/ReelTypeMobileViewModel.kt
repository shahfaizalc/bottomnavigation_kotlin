package com.reelme.app.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.MultipleClickHandler.Companion.handleMultipleClicks
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*

class ReelTypeMobileViewModel(private val context: Context, private val fragmentSignin: FragmentReelType1) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    // from ad mob test id
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

    // from ad mob
    // private val AD_UNIT_ID = "ca-app-pub-7218101052695054/1826851631"

    private var mInterstitialAd: InterstitialAd? = null

    private var mAdIsLoading: Boolean = false

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

        loadAd()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
                fragmentSignin, AD_UNIT_ID, adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError?.message)
                        mInterstitialAd = null
                        mAdIsLoading = false
                        val error = "domain: ${adError.domain}, code: ${adError.code}, " +
                                "message: ${adError.message}"

                        Log.d(TAG, "Ad was not loaded.$error")

                        Toast.makeText(
                                fragmentSignin,
                                "onAdFailedToLoad() with error $error",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                        mAdIsLoading = false
                      //  Toast.makeText(fragmentSignin, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                    }
                }
        )

    }

    private fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                    loadAd()
                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReelDailyBonus::class.java));
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d(TAG, "Ad failed to show.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd?.show(fragmentSignin)
        } else {
            Toast.makeText(fragmentSignin, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            loadAd()
        }
    }


   public fun signInUserClicked() {
        //  fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReelAdventures::class.java));

    }


   public fun signUpUserClicked() {
        onFilterClick()
    }



    fun onSkipButtonClicked() {
        // fragmentSignin.finish()
        userDetails.skipReferalCode = true;

        //  setUserInfo()
    }


    lateinit var userDetails : UserModel
    private var isEdit = false

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT",false)

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


    fun setUserInfo(){

        progressBarVisible = View.VISIBLE

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.putBoolean("IS_EDIT",false)
        editor.apply()
        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                if(isEdit){
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else{
                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));
                }

                Log.d("Authenticaiton token", "onSuccess")
                //   Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                progressBarVisible = View.INVISIBLE
                Log.d("Authenticaiton token", "Exception$e")
                Toast.makeText(context, "Failed to update your profile.. please try again later", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
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
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }


    fun onFilterClick() {

        if (!handleMultipleClicks()) {

            val dialog = Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen)

            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_googlead)

            val btndialogYes: TextView = dialog.findViewById(R.id.share_yes) as TextView
            btndialogYes.setOnClickListener {
                showInterstitial();
                dialog.dismiss()
            }

            val btndialogNo: TextView = dialog.findViewById(R.id.share_no) as TextView
            btndialogNo.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    companion object {

        val TAG = "ReelTypeMobileViewModel"

    }
}
