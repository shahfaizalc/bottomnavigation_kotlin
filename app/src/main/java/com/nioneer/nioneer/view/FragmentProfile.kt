package com.nioneer.nioneer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentProfileBinding
import com.nioneer.nioneer.databinding.FragmentWelcomeBinding
import com.nioneer.nioneer.fragments.BaseFragment
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.viewmodel.ProfileEditViewModel
import com.nioneer.nioneer.viewmodel.ProfileViewModel



class FragmentProfile : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ProfileViewModel

    @Transient
    var binding : FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) {
              binding = DataBindingUtil.setContentView(this, R.layout.fragment_profile)
            areaViewModel = ProfileViewModel(this)
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
