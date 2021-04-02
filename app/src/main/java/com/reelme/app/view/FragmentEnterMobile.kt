package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentEntermobileBinding
import com.reelme.app.viewmodel.EnterMobileViewModel


class FragmentEnterMobile : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: EnterMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentEntermobileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_entermobile)
        areaViewModel = EnterMobileViewModel(this, this)
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
