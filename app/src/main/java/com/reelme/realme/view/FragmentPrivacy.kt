package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentPrivacyBinding
import com.reelme.realme.viewmodel.PrivacyViewModel


class FragmentPrivacy : Activity() {

    var binding: FragmentPrivacyBinding? = null

    @Transient
    lateinit internal var areaViewModel: PrivacyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_privacy)
            areaViewModel = PrivacyViewModel(this, this)
            binding!!.homeData = areaViewModel
        }
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