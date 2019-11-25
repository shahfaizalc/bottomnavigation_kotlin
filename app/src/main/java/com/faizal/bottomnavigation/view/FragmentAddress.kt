package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentPostadpricingBinding
import com.faizal.bottomnavigation.databinding.FragmentAddressBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.utils.Constants.POSTAD_OBJECT
import com.faizal.bottomnavigation.viewmodel.AdressViewModel
import com.faizal.bottomnavigation.viewmodel.PostAdPricingViewModel
import com.google.type.PostalAddress


class FragmentAddress : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        val binding = DataBindingUtil.inflate<FragmentAddressBinding>(inflater, R.layout.fragment_address, container, false)
        val areaViewModel = AdressViewModel(activity!!, this,postAdObj)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

}
