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
import com.guiado.akbhar.databinding.ContentCoronaBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.CoronaModel


class FragmentCorona : BaseFragment() {

    var TAG = "FragmentCorona";

    @Transient
    var binding: ContentCoronaBinding? = null;
    private var adView: AdView? = null

    @Transient
    private var interstitialAd: InterstitialAd? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentCoronaBinding>(inflater, R.layout.content_corona, container, false)
            val areaViewModel = CoronaModel(activity!!, this)
            binding?.adSearchModel = areaViewModel

            // adView = AdView(this.activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
            //first banner
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

            //native_banner2
            interstitialAd = InterstitialAd(this.activity, "986915311744880_987735298329548")
            // Set listeners for the Interstitial Ad
            // Set listeners for the Interstitial Ad
            interstitialAd!!.setAdListener(object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Interstitial ad displayed.")
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    // Interstitial dismissed callback
                    Log.e(TAG, "Interstitial ad dismissed.")
                }

                override fun onError(ad: Ad, adError: AdError) {
                    // Ad error callback
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
                }

                override fun onAdLoaded(ad: Ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
                    // Show the ad
                    interstitialAd!!.show()
                }

                override fun onAdClicked(ad: Ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Interstitial ad clicked!")
                }

                override fun onLoggingImpression(ad: Ad) {
                    // Ad impression logged callback
                    Log.d(TAG, "Interstitial ad impression logged!")
                }
            })

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            interstitialAd!!.loadAd()
        }


        return binding!!.root

    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }


}
