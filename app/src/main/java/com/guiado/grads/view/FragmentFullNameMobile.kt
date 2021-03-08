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
import com.guiado.grads.databinding.FragmentFullnameBinding
import com.guiado.grads.viewmodel.EnterFullNameViewModel
import com.guiado.grads.viewmodel.EnterMobileViewModel
import java.io.File


class FragmentFullNameMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: EnterFullNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentFullnameBinding = DataBindingUtil.setContentView(this, R.layout.fragment_fullname)
        areaViewModel = EnterFullNameViewModel(this, this)
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
