package com.reelme.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentEditprofileBinding
import com.reelme.app.viewmodel.EditProfileViewModel


class FragmentEditProfile : AppCompatActivity() {

    @Transient
    internal lateinit var areaViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentEditprofileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_editprofile)
        areaViewModel = EditProfileViewModel(this, this)
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
