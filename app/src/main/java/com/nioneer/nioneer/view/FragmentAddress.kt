package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentPostadpricingBinding
import com.nioneer.nioneer.databinding.FragmentAddressBinding
import com.nioneer.nioneer.utils.Constants.POSTAD_OBJECT
import com.nioneer.nioneer.viewmodel.AdressViewModel


class FragmentAddress : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    val postAdObj  = intent.extras!!.getString(POSTAD_OBJECT)
    val binding : FragmentAddressBinding = DataBindingUtil.setContentView(this, R.layout.fragment_address)
        val areaViewModel = AdressViewModel( this,postAdObj!!)
        binding.postAdPricing = areaViewModel
    }

}
