package com.guiado.akbhar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.facebook.ads.*
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ActivityWebviewPrayerBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.utils.Constants.LANGUAGE_ID
import com.guiado.akbhar.utils.SharedPreference
import com.guiado.akbhar.viewmodel.WebViewPrayerModel
import javax.inject.Inject


/**
 * the activity to show the blog article on web view based on the user selection.
 */

class WebViewPrayerActivity : BaseFragment(), NetworkChangeHandler.NetworkChangeListener {

    //TAG: Class name
    private val TAG = "WebViewActivity"

    //To handle the network state change
    @Inject
    lateinit var networkStateHandler: NetworkChangeHandler

    private var adView: AdView? = null

    //Binding
    @Transient
    lateinit var binding: ActivityWebviewPrayerBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }


    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate<ActivityWebviewPrayerBinding>(inflater, R.layout.activity_webview_prayer, container, false)

        val pref = SharedPreference(activity!!.applicationContext)
        var languageId = pref.getValueString(LANGUAGE_ID)!!.toLowerCase()


        binding.webViewData = WebViewPrayerModel(activity!!)
        binding.webViewData!!.webViewUrl = "file:///android_asset/praytime_" + languageId + ".htm"
        binding.executePendingBindings()
        networkStateHandler = NetworkChangeHandler()
        //adView = AdView(this.activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
        //banner4
        adView = AdView(this.activity, "986915311744880_986915845078160", AdSize.BANNER_HEIGHT_50)

        // Find the Ad Container
        val adContainer = binding!!.bannerContainer as LinearLayout

        // Add the ad view to your activity layout
        adContainer.addView(adView)

        adView!!.setAdListener(object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback

                Log.d("dear", "racha Error: " + adError.errorMessage)

            }

            override fun onAdLoaded(ad: Ad) {
                // Ad loaded callback
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
            }
        })

        // Request an ad
        adView!!.loadAd()

        return binding!!.root

    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }


    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    /**
     * Register network state handler
     */
    fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(activity!!.applicationContext)
        networkStateHandler.setNetworkStateListener(this)
    }

    /**
     * To Unregister network state handler
     */
    fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(activity!!.applicationContext)
    }

    /**
     * To handle on network state change received.
     * @param online: network state
     */
    override fun networkChangeReceived(state: Boolean) {
        Log.d(TAG, "onNetWorkStateReceived :$state")
        with(binding.webViewData!!) {
            when (state) {
                true -> {
                    msgView = android.view.View.GONE
                }
                false -> {
                    msgView = android.view.View.VISIBLE
                    msg = activity!!.applicationContext.resources.getString(R.string.network_ErrorMsg)
                }
            }
        }
    }
}