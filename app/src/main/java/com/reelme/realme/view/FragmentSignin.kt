package com.reelme.realme.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.reelme.realme.R
import com.reelme.realme.databinding.FragmentSigninBinding
import com.reelme.realme.viewmodel.SignInViewModel


class FragmentSignin : Activity() {

    @Transient
    lateinit internal var areaViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentSigninBinding = DataBindingUtil.setContentView(this, R.layout.fragment_signin)
        areaViewModel = SignInViewModel(this, this)
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
