package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.viewmodel.BioViewModel
import com.reelme.app.databinding.FragmentBioBinding


class FragmentBioMobile : AppCompatActivity() {

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
