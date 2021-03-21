package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentVerifymobileBinding
import com.reelme.app.viewmodel.VerifyMobileViewModel


class FragmentVerifyMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: VerifyMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentVerifymobileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_verifymobile)
        areaViewModel = VerifyMobileViewModel(this, this)
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
