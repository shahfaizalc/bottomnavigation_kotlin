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


class FragmentImgSelction : Activity() {

    @Transient
    lateinit internal var areaViewModel: ImgselectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentImgselectionBinding = DataBindingUtil.setContentView(this, R.layout.fragment_imgselection)
        areaViewModel = ImgselectionViewModel(this, this)
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
