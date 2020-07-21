package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentEventBinding
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.viewmodel.EventViewModel


class FragmentEvent : Activity() {

    @Transient
    lateinit internal var areaViewModel: EventViewModel

    lateinit var binding : FragmentEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_event)
        areaViewModel = EventViewModel( this,postAdObj!!)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.postDiscussion
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
