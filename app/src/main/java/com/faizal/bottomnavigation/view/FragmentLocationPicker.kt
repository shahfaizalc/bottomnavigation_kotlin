package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.activities.Main2Activity
import com.faizal.bottomnavigation.databinding.FragmentFindrideBinding
import com.faizal.bottomnavigation.databinding.FragmentHome2Binding
import com.faizal.bottomnavigation.databinding.FragmentLocationpickerBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.FindViewModel
import com.faizal.bottomnavigation.viewmodel.HomeViewModel
import com.faizal.bottomnavigation.viewmodel.LocationPickerViewModel


class FragmentLocationPicker : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: LocationPickerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentLocationpickerBinding>(inflater, R.layout.fragment_locationpicker, container, false)
        areaViewModel = LocationPickerViewModel(this.context!!, this)
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

//    fun showBottom(){
//
//        (activity as Main2Activity).bottomLayout(View.VISIBLE)
//
//    }
}