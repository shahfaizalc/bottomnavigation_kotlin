package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentAboutBinding
import com.guiado.grads.databinding.FragmentFeedbackBinding
import com.guiado.grads.databinding.FragmentPrivacyBinding
import com.guiado.grads.databinding.FragmentProfileeditBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.AboutViewModel
import com.guiado.grads.viewmodel.FeedbackViewModel
import com.guiado.grads.viewmodel.PrivacyViewModel


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
