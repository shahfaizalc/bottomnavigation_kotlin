package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentFullnameBinding
import com.reelme.realme.viewmodel.EnterFullNameViewModel


class FragmentFullNameMobile : Activity() {

    @Transient
    lateinit internal var areaViewModel: EnterFullNameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentFullnameBinding = DataBindingUtil.setContentView(this, R.layout.fragment_fullname)
        areaViewModel = EnterFullNameViewModel(this, this)
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
