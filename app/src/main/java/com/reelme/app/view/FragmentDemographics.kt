package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentDemographicsBinding
import com.reelme.app.viewmodel.DemographicsViewModel
import com.reelme.app.viewmodel.EnterFullNameViewModel


class FragmentDemographics : AppCompatActivity() {

    @Transient
    internal lateinit var areaViewModel: DemographicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentDemographicsBinding = DataBindingUtil.setContentView(this, R.layout.fragment_demographics)
        areaViewModel = DemographicsViewModel(this, this)
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
