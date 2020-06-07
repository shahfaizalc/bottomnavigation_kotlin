package com.guiado.grads.view

import android.app.Activity
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


class FragmentAddress : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    val postAdObj  = intent.extras!!.getString(POSTAD_OBJECT)
    val binding : FragmentAddressBinding = DataBindingUtil.setContentView(this, R.layout.fragment_address)
        val areaViewModel = AdressViewModel( this,postAdObj!!)
        binding.postAdPricing = areaViewModel
    }

}
