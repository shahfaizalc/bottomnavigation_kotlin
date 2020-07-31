package com.guiado.akbhar.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.facebook.ads.*
import com.guiado.akbhar.R
import com.guiado.akbhar.adapter.CustomPagerAdapter
import com.guiado.akbhar.databinding.ContentMoroccoBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.MoroccoViewModel


class FragmentMorocco : BaseFragment() {

    @Transient
    var binding: ContentMoroccoBinding? = null;


    @Transient
    private var adView: AdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentMoroccoBinding>(inflater, R.layout.content_morocco, container, false)
            val areaViewModel = MoroccoViewModel(activity!!, this)
            binding?.adSearchModel = areaViewModel

//        val viewPager = binding!!.viewpager
//        viewPager.adapter = CustomPagerAdapter(this.activity!!)
//        viewPager.autoScroll(3000)
             // adView = AdView(this.activity, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)
            //banner4
            adView = AdView(this.activity, "986915311744880_989632448139833", AdSize.BANNER_HEIGHT_50)


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
        return binding!!.root
    }


    override fun onDestroy() {
//        adView.destroy()
        super.onDestroy()
    }


}
