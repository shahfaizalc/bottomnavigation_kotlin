package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentPostadpricingBinding
import com.guiado.koodal.databinding.FragmentAddressBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.utils.Constants.POSTAD_OBJECT
import com.guiado.koodal.viewmodel.AdressViewModel


class FragmentAddress : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj!!)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        val binding = DataBindingUtil.inflate<FragmentAddressBinding>(inflater, R.layout.fragment_address, container, false)
        val areaViewModel = AdressViewModel(activity!!, this,postAdObj)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

}
