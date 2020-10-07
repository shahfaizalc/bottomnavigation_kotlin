package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentAboutBinding
import com.nioneer.nioneer.databinding.FragmentFeedbackBinding
import com.nioneer.nioneer.databinding.FragmentPrivacyBinding
import com.nioneer.nioneer.databinding.FragmentProfileeditBinding
import com.nioneer.nioneer.viewmodel.AboutViewModel


class FragmentAbout : Activity() {

    var binding: FragmentAboutBinding? = null

    @Transient
    lateinit internal var areaViewModel: AboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_about)
            areaViewModel = AboutViewModel(this, this)
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
