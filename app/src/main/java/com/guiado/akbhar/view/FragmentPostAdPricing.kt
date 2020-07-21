package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentPostadpricingBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.model.PostAdModel
import com.guiado.akbhar.utils.Constants.POSTAD_OBJECT
import com.guiado.akbhar.viewmodel.PostAdPricingViewModel


class FragmentPostAdPricing : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getParcelable<PostAdModel>(POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj!!)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: PostAdModel): View {
        val binding = DataBindingUtil.inflate<ContentPostadpricingBinding>(inflater, R.layout.content_postadpricing, container, false)
        val areaViewModel = PostAdPricingViewModel(activity!!, this,postAdObj)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

}
