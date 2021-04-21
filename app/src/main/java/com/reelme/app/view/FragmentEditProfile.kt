package com.reelme.app.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

    override fun onStart() {
        super.onStart()
        areaViewModel.registerListeners()
    }
    override fun onResume() {
        super.onResume()
        System.out.println("returnretern")
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2)
        {
            areaViewModel.getUserInfo()

            val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putBoolean("HAS_CHANGES",true)
            editor.apply()
        }
    }
}
