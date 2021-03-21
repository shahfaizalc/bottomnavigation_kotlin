package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentForgotpasswordBinding
import com.reelme.realme.viewmodel.ForgotPasswordViewModel


class FragmentForgotPassword : Activity() {

    @Transient
    lateinit internal var areaViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentForgotpasswordBinding = DataBindingUtil.setContentView(this, R.layout.fragment_forgotpassword)
        areaViewModel = ForgotPasswordViewModel(this, this)
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
