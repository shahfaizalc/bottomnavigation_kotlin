package com.guiado.akbhar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.facebook.ads.*
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentCoronaBinding
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.CoronaModel
import com.guiado.akbhar.viewmodel.DiscussionModel


class FragmentCorona : BaseFragment() {

    @Transient
    var binding: ContentCoronaBinding? = null;
    private var adView: AdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentCoronaBinding>(inflater, R.layout.content_corona, container, false)
            val areaViewModel = CoronaModel(activity!!, this)
            binding?.adSearchModel = areaViewModel

        adView = AdView(this.activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
        // adView = AdView(this, "986915311744880_986915845078160", AdSize.BANNER_HEIGHT_50)


        // Find the Ad Container
        val adContainer = binding!!.bannerContainer as LinearLayout

        // Add the ad view to your activity layout
        adContainer.addView(adView)

        adView!!.setAdListener(object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback

                Log.d("dear","racha Error: " + adError.errorMessage)

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
        return binding!!.root

    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }


}
