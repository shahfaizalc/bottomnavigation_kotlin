package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.FragmentAboutBinding
import com.guiado.koodal.databinding.FragmentFeedbackBinding
import com.guiado.koodal.databinding.FragmentPrivacyBinding
import com.guiado.koodal.databinding.FragmentProfileeditBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.AboutViewModel


class FragmentAbout : BaseFragment() {

    var binding: FragmentAboutBinding? = null

    @Transient
    lateinit internal var areaViewModel: AboutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
            areaViewModel = AboutViewModel(this.context!!, this)
            binding!!.homeData = areaViewModel
        }
        return binding!!.root
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
