package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentGamechooserBinding
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.viewmodel.GameChooserModel

class FragmentGameChooser : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        val binding : FragmentGamechooserBinding = DataBindingUtil.setContentView(this, R.layout.fragment_gamechooser)
        val areaViewModel = GameChooserModel( this,postAdObj)
        binding.gameChooserModel = areaViewModel
    }

}