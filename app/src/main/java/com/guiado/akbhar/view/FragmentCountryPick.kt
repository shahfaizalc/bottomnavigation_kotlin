package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.LayoutmainBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.UserViewModel

class FragmentCountryPick : BaseFragment() {


    private val viewModel = UserViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<LayoutmainBinding>(inflater, R.layout.layoutmain, container, false)
        val areaViewModel = UserViewModel()
        binding.viewModel = areaViewModel
        return binding.root
    }

}