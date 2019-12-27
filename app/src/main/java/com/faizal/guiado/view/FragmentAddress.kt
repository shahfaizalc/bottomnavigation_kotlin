package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.ContentPostadpricingBinding
import com.faizal.guiado.databinding.FragmentAddressBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.utils.Constants.POSTAD_OBJECT
import com.faizal.guiado.viewmodel.AdressViewModel


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
