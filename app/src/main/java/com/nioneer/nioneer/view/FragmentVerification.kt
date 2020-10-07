package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentRideBinding
import com.nioneer.nioneer.databinding.FragmentVerifiedBinding
import com.nioneer.nioneer.viewmodel.VerifiedViewModel



class FragmentVerification : Activity() {

    @Transient
    lateinit internal var areaViewModel: VerifiedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentVerifiedBinding = DataBindingUtil.setContentView(this, R.layout.fragment_verified)
        areaViewModel = VerifiedViewModel( this)
        binding.homeData = areaViewModel
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
