package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentEventlistBinding
import com.faizal.bottomnavigation.databinding.FragmentGamechooserBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.EventListModel
import com.faizal.bottomnavigation.viewmodel.GameChooserModel

class FragmentEventList : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentEventlistBinding>(inflater, R.layout.fragment_eventlist, container, false)
        val areaViewModel = EventListModel(this)
        binding.gameChooserModel = areaViewModel
        return binding.root
    }

}