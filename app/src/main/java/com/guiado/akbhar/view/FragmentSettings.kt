package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentFindrideBinding
import com.guiado.akbhar.databinding.FragmentInfoBinding
import com.guiado.akbhar.databinding.FragmentSettingsBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.SettingsViewModel


class FragmentSettings : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(inflater, R.layout.fragment_settings, container, false)
        areaViewModel = SettingsViewModel(this.context!!, this)
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
