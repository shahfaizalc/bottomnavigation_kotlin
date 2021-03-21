package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.viewmodel.BioViewModel
import com.reelme.realme.databinding.FragmentBioBinding


class FragmentBioMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: BioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentBioBinding = DataBindingUtil.setContentView(this, R.layout.fragment_bio)
        areaViewModel = BioViewModel(this, this)
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
