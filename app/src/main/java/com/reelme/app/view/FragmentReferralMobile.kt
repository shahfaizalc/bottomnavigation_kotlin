package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentReferralmobileBinding
import com.reelme.app.viewmodel.RefferalMobileViewModel


class FragmentReferralMobile : AppCompatActivity() {

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
