package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentWelcomeBinding
import com.reelme.realme.viewmodel.WelcomeViewModel


class FragmentWelcome : Activity() {

    @Transient
    lateinit internal var areaViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_welcome)
        areaViewModel = WelcomeViewModel(this, this)
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
