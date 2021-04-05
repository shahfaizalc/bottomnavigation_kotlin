package com.reelme.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentSetttinglistBinding
import com.reelme.app.viewmodel.DemographicsViewModel
import com.reelme.app.viewmodel.EnterFullNameViewModel
import com.reelme.app.viewmodel.SettingListViewModel


class FragmentSettingsList : AppCompatActivity() {

    @Transient
    internal lateinit var areaViewModel: SettingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentSetttinglistBinding = DataBindingUtil.setContentView(this, R.layout.fragment_setttinglist)
        areaViewModel = SettingListViewModel(this, this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2)
        {
            areaViewModel.getUserInfo()
        }
    }
}
