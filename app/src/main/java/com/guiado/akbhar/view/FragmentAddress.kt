package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentPostadpricingBinding
import com.guiado.akbhar.databinding.FragmentAddressBinding
import com.guiado.akbhar.utils.Constants.POSTAD_OBJECT
import com.guiado.akbhar.viewmodel.AdressViewModel


class FragmentAddress : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    val postAdObj  = intent.extras!!.getString(POSTAD_OBJECT)
    val binding : FragmentAddressBinding = DataBindingUtil.setContentView(this, R.layout.fragment_address)
        val areaViewModel = AdressViewModel( this,postAdObj!!)
        binding.postAdPricing = areaViewModel
    }

}
