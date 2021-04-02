package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.*
import com.reelme.app.viewmodel.*


class FragmentUserName : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: UsernameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentUsernameBinding = DataBindingUtil.setContentView(this, R.layout.fragment_username)
        areaViewModel = UsernameViewModel(this, this)
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
