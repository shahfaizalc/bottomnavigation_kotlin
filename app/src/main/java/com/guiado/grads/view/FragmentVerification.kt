package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentVerifiedBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.VerifiedViewModel


class FragmentVerification : Activity() {

    @Transient
    lateinit internal var areaViewModel: VerifiedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentVerifiedBinding = DataBindingUtil.setContentView(this, R.layout.fragment_verified)
        areaViewModel = VerifiedViewModel( this)
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
