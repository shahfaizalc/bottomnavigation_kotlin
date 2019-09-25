package com.faizal.bottomnavigation.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentPostadpricingBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.utils.Constants.POSTAD_OBJECT
import com.faizal.bottomnavigation.viewmodel.PostAdPricingViewModel


class FragmentPostAdPricing : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getParcelable<PostAdModel>(POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: PostAdModel): View {
        val binding = DataBindingUtil.inflate<ContentPostadpricingBinding>(inflater, R.layout.content_postadpricing, container, false)
        val areaViewModel = PostAdPricingViewModel(activity!!, this,postAdObj)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

}
