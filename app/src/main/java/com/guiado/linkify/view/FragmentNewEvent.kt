package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentNeweventBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.*


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
