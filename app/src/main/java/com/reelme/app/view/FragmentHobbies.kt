package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentHobbiesBinding
import com.reelme.app.viewmodel.HobbiesViewModel


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
