package com.nioneer.nioneer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentRideBinding
import com.nioneer.nioneer.databinding.FragmentWelcomeBinding
import com.nioneer.nioneer.fragments.BaseFragment
import com.nioneer.nioneer.viewmodel.RideViewModel


class FragmentRide : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: RideViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentRideBinding>(inflater, R.layout.fragment_ride, container, false)
        areaViewModel = RideViewModel(this.context!!, this)
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
