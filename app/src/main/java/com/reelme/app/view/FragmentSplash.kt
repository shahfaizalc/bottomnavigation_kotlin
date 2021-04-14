package com.reelme.app.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentSplashBinding
import com.reelme.app.viewmodel.SplashViewModel
import java.security.AccessController.getContext
import java.util.*


class FragmentSplash : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentSplashBinding = DataBindingUtil.setContentView(this, R.layout.fragment_splash)
        areaViewModel = SplashViewModel(this, this)
        binding.homeData = areaViewModel

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, FragmentWelcome::class.java);

            val bundle = ActivityOptionsCompat.makeCustomAnimation(this,
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
            startActivity(intent, bundle)

//            startActivity(intent, bundle)
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
