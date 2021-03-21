package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.*
import com.reelme.realme.viewmodel.*


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
