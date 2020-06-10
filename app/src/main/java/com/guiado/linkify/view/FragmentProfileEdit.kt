package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentProfileeditBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.ProfileEditViewModel


class FragmentProfileEdit : Activity() {

    var binding: FragmentProfileeditBinding? = null

    @Transient
    lateinit internal var areaViewModel: ProfileEditViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_profileedit)
            areaViewModel = ProfileEditViewModel(this, this,postAdObj!!)
            binding!!.homeData = areaViewModel
            binding!!.profile = areaViewModel.profile
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
