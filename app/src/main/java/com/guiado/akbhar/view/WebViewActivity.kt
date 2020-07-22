package com.guiado.akbhar.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.ads.*
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ActivityWebviewBinding
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.viewmodel.WebViewModel
import javax.inject.Inject


/**
 * the activity to show the blog article on web view based on the user selection.
 */

class WebViewActivity : AppCompatActivity(), NetworkChangeHandler.NetworkChangeListener{

    //TAG: Class name
    private val TAG = "WebViewActivity"

    //To handle the network state change
    @Inject
    lateinit var networkStateHandler: NetworkChangeHandler

    private var adView: AdView? = null

    //Binding
    @Transient
    lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blogUrl = intent.getStringExtra(Constants.POSTAD_OBJECT)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        binding.webViewData = WebViewModel()
        binding.webViewData!!.webViewUrl = blogUrl
        binding.executePendingBindings()
        networkStateHandler = NetworkChangeHandler()
       // adView = AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
        //banner2
        adView = AdView(this, "986915311744880_987732841663127", AdSize.BANNER_HEIGHT_50)

        // Find the Ad Container
        val adContainer = findViewById<View>(R.id.banner_container) as LinearLayout

        // Add the ad view to your activity layout
        adContainer.addView(adView)

        adView!!.setAdListener(object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Toast.makeText(this@WebViewActivity, "Error: " + adError.errorMessage,
                        Toast.LENGTH_LONG).show()
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
      //  adView!!.loadAd()
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }
    /**
     * Register network state handler
     */
    fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(this)
        networkStateHandler.setNetworkStateListener(this)
    }

    /**
     * To Unregister network state handler
     */
    fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(this)
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
                    msg = applicationContext.resources.getString(R.string.network_ErrorMsg)
                }
            }
        }    }
}