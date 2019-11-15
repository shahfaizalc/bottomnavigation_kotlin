package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentRideBinding
import com.faizal.bottomnavigation.databinding.FragmentStopoverBinding
import com.faizal.bottomnavigation.databinding.FragmentWelcomeBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.RideViewModel
import com.faizal.bottomnavigation.viewmodel.StopOversViewModel
import com.faizal.bottomnavigation.viewmodel.WelcomeViewModel


class FragmentStopOvers : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: StopOversViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentStopoverBinding>(inflater, R.layout.fragment_stopover, container, false)
        areaViewModel = StopOversViewModel(this.context!!, this)
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
