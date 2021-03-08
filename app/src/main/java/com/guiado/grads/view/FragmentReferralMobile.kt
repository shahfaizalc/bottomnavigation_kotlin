package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentWelcomeBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.databinding.FragmentEntermobileBinding
import com.guiado.grads.databinding.FragmentReferralmobileBinding
import com.guiado.grads.databinding.FragmentVerifymobileBinding
import com.guiado.grads.viewmodel.EnterMobileViewModel
import com.guiado.grads.viewmodel.RefferalMobileViewModel
import com.guiado.grads.viewmodel.VerifyMobileViewModel
import java.io.File


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
