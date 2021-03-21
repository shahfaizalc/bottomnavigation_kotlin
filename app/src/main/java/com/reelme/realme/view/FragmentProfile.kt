package com.reelme.realme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentProfileBinding
import com.reelme.realme.fragments.BaseFragment
import com.reelme.realme.viewmodel.ProfileViewModel


class FragmentProfile : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)
        areaViewModel = ProfileViewModel(activity!!, this.context!!, this)
        binding.homeData = areaViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
