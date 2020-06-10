package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentRideBinding
import com.guiado.linkify.databinding.FragmentVerifiedBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.VerifiedViewModel



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
