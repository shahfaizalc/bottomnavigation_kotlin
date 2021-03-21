package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentEntermobileBinding
import com.reelme.realme.viewmodel.EnterMobileViewModel


class FragmentEnterMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: EnterMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentEntermobileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_entermobile)
        areaViewModel = EnterMobileViewModel(this, this)
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
