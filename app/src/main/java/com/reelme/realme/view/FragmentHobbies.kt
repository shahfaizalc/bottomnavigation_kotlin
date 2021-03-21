package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentHobbiesBinding
import com.reelme.realme.viewmodel.HobbiesViewModel


class FragmentHobbies : Activity() {

    @Transient
    lateinit internal var areaViewModel: HobbiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentHobbiesBinding = DataBindingUtil.setContentView(this, R.layout.fragment_hobbies)
        areaViewModel = HobbiesViewModel(this, this)
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
