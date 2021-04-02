package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentEditprofileBinding
import com.reelme.app.databinding.FragmentUserprofileBinding
import com.reelme.app.viewmodel.EditProfileViewModel
import com.reelme.app.viewmodel.UserProfileViewModel


class FragmentUserProfile : AppCompatActivity() {

    @Transient
    internal lateinit var areaViewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentUserprofileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_userprofile)
        areaViewModel = UserProfileViewModel(this, this)
        binding.homeData = areaViewModel
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
