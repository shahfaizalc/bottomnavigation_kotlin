package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentEventBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.utils.Constants
import com.guiado.grads.viewmodel.EventViewModel


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
