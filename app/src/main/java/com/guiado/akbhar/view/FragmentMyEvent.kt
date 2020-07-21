package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentMyeventBinding
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.viewmodel.MyEventViewModel


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
