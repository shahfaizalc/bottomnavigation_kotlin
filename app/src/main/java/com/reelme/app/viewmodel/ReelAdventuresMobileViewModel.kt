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
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.AdventureTopicsResultListener
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.SkipTopicsResultListener
import com.reelme.app.model2.AdventuresTopics
import com.reelme.app.model2.SkipTopics
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*

class ReelAdventuresMobileViewModel(private val context: Context, private val fragmentSignin: FragmentReelAdventures)
    : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    // from ad mob test id
    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

    // from ad mob
    // private val AD_UNIT_ID = "ca-app-pub-7218101052695054/1826851631"

    private var mInterstitialAd: InterstitialAd? = null

    private var mAdIsLoading: Boolean = false

    private val currentFirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    var headline1_Points = "";
    var headline1_title = "";
    var headline2_Points = "";
    var headline2_title = "";


    init {
        networkHandler()
        getUserInfo()

        // Initialize the Mobile Ads SDK.
        //  MobileAds.initialize(fragmentSignin) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
//        MobileAds.setRequestConfiguration(
//                RequestConfiguration.Builder()
//                        .setTestDeviceIds(listOf("74E96DD154EF70EBE92C022E3505DE45"))
//                        .build()
//        )

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
        loadData()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
                fragmentSignin, AD_UNIT_ID, adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(ReelTypeMobileViewModel.TAG, adError?.message)
                        mInterstitialAd = null
                        mAdIsLoading = false
                        val error = "domain: ${adError.domain}, code: ${adError.code}, " +
                                "message: ${adError.message}"

                        Log.d(ReelTypeMobileViewModel.TAG, "Ad was not loaded.$error")

                        Toast.makeText(
                                fragmentSignin,
                                "onAdFailedToLoad() with error $error",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d(ReelTypeMobileViewModel.TAG, "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                        mAdIsLoading = false
                      //  Toast.makeText(fragmentSignin, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                    }
                }
        )

    }

    private fun showInterstitial(i: Int) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(ReelTypeMobileViewModel.TAG, "Ad was dismissed.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                    loadAd()

                    if (i == 0) {
                        loadMoreA()
                    } else {
                        loadMoreB()
                    }


                    // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentReelDailyBonus::class.java));
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d(ReelTypeMobileViewModel.TAG, "Ad failed to show.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(ReelTypeMobileViewModel.TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd?.show(fragmentSignin)
        } else {
            Toast.makeText(fragmentSignin, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            loadAd()
        }
    }


    var adventureBonusTopics = ArrayList<AdventuresTopics>();

    var skipTopics = ArrayList<SkipTopics>();


    var totalItems = 0
    var aItem = 0;
    var bItem = 0

    fun loadMoreA() {
        var typeAAdded = false;
        for ((count, topics) in adventureBonusTopics.withIndex()) {
            if (count > aItem) {
                var isAvailable = false
                for (skipTopic in skipTopics) {
                    if (skipTopic.topicId == topics.topicId) {
                        isAvailable = true
                    }
                }

                if (!isAvailable) {
                    if (topics.type.equals("A", true)) {
                        if (!typeAAdded) {

                            loadTypeAItem(topics, count)
                            typeAAdded = true
                        }
                    }
                }
            }
        }
    }

    fun loadMoreB() {
        var typeBAdded = false;
        for ((count, topics) in adventureBonusTopics.withIndex()) {
            if (count > aItem) {
                var isAvailable = false
                for (skipTopic in skipTopics) {
                    if (skipTopic.topicId == topics.topicId) {
                        isAvailable = true
                    }
                }

                if (!isAvailable) {
                    if (topics.type.equals("B", true)) {
                        if (!typeBAdded) {
                            loadTypeBItems(topics, count)
                            typeBAdded = true
                        }

                    }
                }
            }
        }
    }

    private fun loadData() {
        val firbaseWriteHandlerActivity = FirbaseWriteHandlerActivity(fragmentSignin)

        firbaseWriteHandlerActivity.doGetSkipTopics(object : SkipTopicsResultListener {
            override fun onSuccess(url: List<SkipTopics>) {
                skipTopics.addAll(url)

                firbaseWriteHandlerActivity.doGetAdventureTopics(object : AdventureTopicsResultListener {

                    override fun onSuccess(adventureList: List<AdventuresTopics>) {
                        Log.d("TAG", "Success bonus topics size " + adventureList.size)
                        adventureBonusTopics.addAll(adventureList);
                        totalItems = adventureList.size
                        when {
                            adventureList.isEmpty() -> {
                                aItem = -1
                                bItem = -1
                                return
                            }
                            adventureBonusTopics.size > 0 -> {
                                var typeAAdded = false;
                                var typeBAdded = false;
                                for ((count, topics) in adventureBonusTopics.withIndex()) {

                                    var isAvailable = false
                                    for (skipTopic in skipTopics) {
                                        if (skipTopic.topicId == topics.topicId) {
                                            isAvailable = true
                                        }
                                    }

                                    if (!isAvailable) {
                                        if (topics.type.equals("A", true)) {
                                            if (!typeAAdded) {
                                                loadTypeAItem(topics, count)
                                                typeAAdded = true
                                            }

                                        } else {
                                            if (!typeBAdded) {
                                                loadTypeBItems(topics, count)
                                                typeBAdded = true
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(e: Exception) {
                        Log.d("TAG", "Failure bonus topics size " + e.message)

                    }
                })

            }

            override fun onFailure(e: Exception) {
            }
        })
    }

    fun onTypeAClick() {
        onFilterClick(adventureBonusTopics[aItem], 0)
    }

    fun onTypeBClick() {
        onFilterClick(adventureBonusTopics[bItem], 1)
    }

    private fun onFilterClick(adventuresTopics: AdventuresTopics, i: Int) {

        if (!MultipleClickHandler.handleMultipleClicks()) {

            val dialog = Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_googlead)

            val btndialogYes: TextView = dialog.findViewById(R.id.share_yes) as TextView
            btndialogYes.setOnClickListener {
                dialog.dismiss()
                onWhySkipClick(adventuresTopics, i)

            }

            val btndialogNo: TextView = dialog.findViewById(R.id.share_no) as TextView
            btndialogNo.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }


    private fun onWhySkipClick(adventuresTopics: AdventuresTopics, i: Int) {

        val dialog = Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_whyskip)

        val btndialogYes: TextView = dialog.findViewById(R.id.share_yes) as TextView
        btndialogYes.setOnClickListener {
            dialog.dismiss()
            saveSkipState(adventuresTopics, 0)
            showInterstitial(i)
        }

        val btndialogNo: TextView = dialog.findViewById(R.id.share_no) as TextView
        btndialogNo.setOnClickListener {
            dialog.dismiss()
            saveSkipState(adventuresTopics, 1)
            showInterstitial(i)
        }
        dialog.show()

    }

    fun loadTypeAItem(item: AdventuresTopics, count: Int) {
        headline1Points = item.points.toString();
        headline1title = item.topicDescription
        aItem = count
    }

    fun loadTypeBItems(item2: AdventuresTopics, count: Int) {
        headline2Points = item2.points.toString()
        headline2title = item2.topicDescription
        aItem = count
    }


    fun loadBothItems(item1: Int, item2: Int) {
        headline1Points = adventureBonusTopics[item1].points.toString();
        headline1title = adventureBonusTopics[item1].topicDescription
        headline2Points = adventureBonusTopics[item2].points.toString()
        headline2title = adventureBonusTopics[item2].topicDescription
        headline1_Points = adventureBonusTopics[item1].points.toString();
        headline1_title = adventureBonusTopics[item1].topicDescription
        headline2_Points = adventureBonusTopics[item2].points.toString()
        headline2_title = adventureBonusTopics[item2].topicDescription

        aItem = item1
        bItem = item2
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

    private fun saveSkipState(adventuresTopics: AdventuresTopics, i: Int) {

        var skipTopics = SkipTopics(System.currentTimeMillis(), neverShow = false, showInEnd = true,
                adventuresTopics.topicId, currentFirebaseUser!!.uid)
        if (i == 1) {
            skipTopics = SkipTopics(System.currentTimeMillis(), neverShow = true, showInEnd = false,
                    adventuresTopics.topicId, currentFirebaseUser.uid)
        }

        progressBarVisible = View.VISIBLE

        FirbaseWriteHandlerActivity(fragmentSignin).updateSkipTopics(skipTopics, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE

                // fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));


                Log.d("updateSkipTopics token", "onSuccess")
                //   Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                progressBarVisible = View.INVISIBLE
                Log.d("updateSkipTopics token", "Exception$e")
                Toast.makeText(context, "Failed to update your profile.. please try again later", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }


//    fun setUserInfo() {
//
//        progressBarVisible = View.VISIBLE
//
//        val gsonValue = Gson().toJson(userDetails)
//
//        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.putString("USER_INFO", gsonValue)
//        editor.putBoolean("IS_EDIT", false)
//        editor.apply()
//        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
//            override fun onSuccess() {
//                progressBarVisible = View.INVISIBLE
//                if (isEdit) {
//                    fragmentSignin.setResult(2, Intent())
//                    fragmentSignin.finish()
//                } else {
//                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentEmailAddress::class.java));
//                }
//
//                Log.d("Authenticaiton token", "onSuccess")
//                //   Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
//
//            }
//
//            override fun onFailure(e: Exception) {
//                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
//                progressBarVisible = View.INVISIBLE
//                Log.d("Authenticaiton token", "Exception$e")
//                Toast.makeText(context, "Failed to update your profile.. please try again later", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
//
//            }
//        })
//    }

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
