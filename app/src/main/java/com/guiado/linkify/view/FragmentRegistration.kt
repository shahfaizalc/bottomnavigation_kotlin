package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentRegistrationBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.RegistrationModel

class FragmentRegistration : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
    }

    private fun bindView(): View {
        val binding: FragmentRegistrationBinding = DataBindingUtil.setContentView(this,
                R.layout.fragment_registration)
        val areaViewModel = RegistrationModel(this, this)
        binding.homeData = areaViewModel
        return binding.root
    }

}
