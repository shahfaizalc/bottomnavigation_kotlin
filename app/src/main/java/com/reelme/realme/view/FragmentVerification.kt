package com.reelme.realme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentVerifiedBinding
import com.reelme.realme.fragments.BaseFragment
import com.reelme.realme.viewmodel.VerifiedViewModel


class FragmentVerification : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: VerifiedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentVerifiedBinding>(inflater, R.layout.fragment_verified, container, false)
        areaViewModel = VerifiedViewModel(this.context!!, this)
        binding.homeData = areaViewModel
        return binding.root
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