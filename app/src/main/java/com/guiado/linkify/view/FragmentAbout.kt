package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentAboutBinding
import com.guiado.linkify.databinding.FragmentFeedbackBinding
import com.guiado.linkify.databinding.FragmentPrivacyBinding
import com.guiado.linkify.databinding.FragmentProfileeditBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.AboutViewModel


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
