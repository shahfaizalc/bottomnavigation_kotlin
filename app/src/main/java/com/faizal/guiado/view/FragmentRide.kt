package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentRideBinding
import com.faizal.guiado.databinding.FragmentWelcomeBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.RideViewModel


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
