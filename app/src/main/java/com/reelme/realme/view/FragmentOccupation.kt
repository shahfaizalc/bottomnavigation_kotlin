package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentOccupationBinding
import com.reelme.realme.viewmodel.OccupationViewModel


class FragmentOccupation : Activity() {

    @Transient
    lateinit internal var areaViewModel: OccupationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentOccupationBinding = DataBindingUtil.setContentView(this, R.layout.fragment_occupation)
        areaViewModel = OccupationViewModel(this, this)
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
