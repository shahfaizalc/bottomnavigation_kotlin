package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.facebook.ads.*
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentLanguageBinding
import com.guiado.akbhar.viewmodel.LanguageModel


class FragmentLanguage : Activity() {

    var TAG = "FragmentCorona";

    @Transient
    var binding: ContentLanguageBinding? = null;
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_language);

            val areaViewModel = LanguageModel(this)
            binding?.adSearchModel = areaViewModel

            // adView = AdView(this.activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
            //first banner
            adView = AdView(this, "986915311744880_986915845078160", AdSize.BANNER_HEIGHT_50)


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
        }

    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }


}
