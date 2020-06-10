package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentForgotpasswordBinding
import com.guiado.linkify.databinding.FragmentSigninBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.ForgotPasswordViewModel


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
