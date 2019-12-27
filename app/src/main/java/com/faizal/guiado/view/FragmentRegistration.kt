package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentRegistrationBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.RegistrationModel


class FragmentRegistration : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentRegistrationBinding>(inflater,
                R.layout.fragment_registration, container, false)
        val areaViewModel = RegistrationModel(activity!!, this)
        binding.homeData = areaViewModel
        return binding.root
    }

}
