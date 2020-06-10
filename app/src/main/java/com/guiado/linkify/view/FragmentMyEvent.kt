package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentMyeventBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.MyEventViewModel


class FragmentMyEvent : Activity() {

    @Transient
    lateinit internal var areaViewModel: MyEventViewModel

    lateinit var binding : FragmentMyeventBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postAdObj  = intent!!.extras!!.getString(Constants.POSTAD_OBJECT)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_myevent)
        areaViewModel = MyEventViewModel(this,postAdObj!!)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.events
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
