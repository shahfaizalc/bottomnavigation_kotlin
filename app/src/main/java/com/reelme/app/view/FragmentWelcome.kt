package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentWelcomeBinding
import com.reelme.app.viewmodel.WelcomeViewModel


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
