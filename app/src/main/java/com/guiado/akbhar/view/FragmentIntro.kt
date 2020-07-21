package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ActivityIntroBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.IntroViewModel

class FragmentIntro : BaseFragment() {

    lateinit var areaViewModel: IntroViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ActivityIntroBinding>(inflater, R.layout.activity_intro, container, false)
        areaViewModel = IntroViewModel(this)
        binding.profileData = areaViewModel
        return binding.root
    }

    override fun onStop() {
        super.onStop()
    }

}