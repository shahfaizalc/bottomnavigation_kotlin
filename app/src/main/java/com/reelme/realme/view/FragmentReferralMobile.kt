package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentReferralmobileBinding
import com.reelme.realme.viewmodel.RefferalMobileViewModel


class FragmentReferralMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: RefferalMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentReferralmobileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_referralmobile)
        areaViewModel = RefferalMobileViewModel(this, this)
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
