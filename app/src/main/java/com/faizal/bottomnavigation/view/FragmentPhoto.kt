package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentPhotoBinding
import com.faizal.bottomnavigation.databinding.FragmentProfileeditBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.PhotoViewModel
import com.faizal.bottomnavigation.viewmodel.ProfileEditViewModel


class FragmentPhoto : BaseFragment() {

    var binding: FragmentPhotoBinding? = null

    @Transient
    lateinit internal var areaViewModel: PhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentPhotoBinding>(inflater, R.layout.fragment_photo, container, false)
            areaViewModel = PhotoViewModel(this.context!!, this)
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
