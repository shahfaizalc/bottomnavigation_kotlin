package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentRegistrationBinding
import com.nioneer.nioneer.viewmodel.RegistrationModel

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
