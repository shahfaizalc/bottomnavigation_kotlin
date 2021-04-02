package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentSigninBinding
import com.reelme.app.viewmodel.SignInViewModel


class FragmentSignin : AppCompatActivity() {

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
