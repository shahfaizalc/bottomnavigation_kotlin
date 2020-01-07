package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentRegistrationBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.RegistrationModel


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
