package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentPostadpricingBinding
import com.guiado.grads.databinding.FragmentAddressBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.utils.Constants.POSTAD_OBJECT
import com.guiado.grads.viewmodel.AdressViewModel


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
