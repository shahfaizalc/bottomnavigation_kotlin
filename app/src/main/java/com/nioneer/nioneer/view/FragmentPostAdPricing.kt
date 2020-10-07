package com.nioneer.nioneer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentPostadpricingBinding
import com.nioneer.nioneer.fragments.BaseFragment
import com.nioneer.nioneer.model.PostAdModel
import com.nioneer.nioneer.utils.Constants.POSTAD_OBJECT
import com.nioneer.nioneer.viewmodel.PostAdPricingViewModel


class FragmentPostAdPricing : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ContentPostadpricingBinding>(inflater, R.layout.content_postadpricing, container, false)
        val areaViewModel = PostAdPricingViewModel(activity!!, this)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

}
