package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentHomescreenBinding
import com.reelme.realme.databinding.FragmentWelcomeBinding
import com.reelme.realme.viewmodel.HomescreenViewModel
import com.reelme.realme.viewmodel.WelcomeViewModel


class FragmentHomescreen : Activity() {

    @Transient
    lateinit internal var areaViewModel: HomescreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentHomescreenBinding = DataBindingUtil.setContentView(this, R.layout.fragment_homescreen)
        areaViewModel = HomescreenViewModel(this, this)
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
