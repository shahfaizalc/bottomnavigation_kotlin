package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentHomescreenBinding
import com.reelme.app.viewmodel.HomescreenViewModel


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
