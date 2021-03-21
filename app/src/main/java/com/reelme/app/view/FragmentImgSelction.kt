package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.*
import com.reelme.app.viewmodel.*


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
