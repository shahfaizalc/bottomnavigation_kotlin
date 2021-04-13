package com.reelme.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonemetadata
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.reelme.app.R
import com.reelme.app.databinding.FragmentSplashBinding
import com.reelme.app.databinding.FragmentWelcomeBinding
import com.reelme.app.viewmodel.SplashViewModel
import com.reelme.app.viewmodel.WelcomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class FragmentSplash : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentSplashBinding = DataBindingUtil.setContentView(this, R.layout.fragment_splash)
        areaViewModel = SplashViewModel(this, this)
        binding.homeData = areaViewModel

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FragmentWelcome::class.java))
            finish()
        }, 2000)
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
