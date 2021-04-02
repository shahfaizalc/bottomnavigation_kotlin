package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentFullnameBinding
import com.reelme.app.viewmodel.EnterFullNameViewModel


class FragmentFullNameMobile : AppCompatActivity() {

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
