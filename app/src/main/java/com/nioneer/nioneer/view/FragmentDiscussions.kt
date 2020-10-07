package com.nioneer.nioneer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentDiscussionBinding
import com.nioneer.nioneer.fragments.BaseFragment
import com.nioneer.nioneer.viewmodel.DiscussionModel


class FragmentDiscussions : BaseFragment() {


    lateinit var adView: AdView
    var binding: ContentDiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentDiscussionBinding>(inflater, R.layout.content_discussion, container, false)
            val areaViewModel = DiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
            adViewLoad(binding!!.root)
        }
        return binding!!.root
    }


    private fun adViewLoad(root: View) {
        adView = AdView(this.context, this.resources.getString(R.string.banner_id), AdSize.BANNER_HEIGHT_50)
        // Find the Ad Container
        val adContainer = root.findViewById(R.id.banner_container) as LinearLayout

        // Add the ad view to your activity layout
        adContainer.addView(adView)
        // Request an ad
        adView.loadAd()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }


}