package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.fragments.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.databinding.*
import com.guiado.grads.viewmodel.*
import java.io.File


class FragmentEmailAddress : Activity() {

    @Transient
    lateinit internal var areaViewModel: EmailAddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentEmailaddressBinding = DataBindingUtil.setContentView(this, R.layout.fragment_emailaddress)
        areaViewModel = EmailAddressViewModel(this, this)
        binding.homeData = areaViewModel
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
