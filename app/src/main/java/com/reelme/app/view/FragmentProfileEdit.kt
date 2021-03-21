package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentProfileeditBinding
import com.reelme.app.utils.Constants
import com.reelme.app.viewmodel.ProfileEditViewModel


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
