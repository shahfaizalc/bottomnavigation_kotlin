package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonemetadata
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.reelme.app.R
import com.reelme.app.databinding.FragmentWelcomeBinding
import com.reelme.app.viewmodel.WelcomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class FragmentWelcome : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding : FragmentWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.fragment_welcome)
        areaViewModel = WelcomeViewModel(this, this)
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
