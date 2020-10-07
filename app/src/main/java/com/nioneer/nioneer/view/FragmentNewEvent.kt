package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentNeweventBinding
import com.nioneer.nioneer.viewmodel.*


class FragmentNewEvent : Activity() {

    var binding: FragmentNeweventBinding? = null

    @Transient
    lateinit internal var areaViewModel: NewEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_newevent)
            areaViewModel = NewEventViewModel(this, this)
            binding!!.homeData = areaViewModel
        }
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
