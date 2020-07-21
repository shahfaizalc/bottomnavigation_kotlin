package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentGamechooserBinding
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.viewmodel.GameChooserModel

class FragmentGameChooser : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        val binding : FragmentGamechooserBinding = DataBindingUtil.setContentView(this, R.layout.fragment_gamechooser)
        val areaViewModel = GameChooserModel( this,postAdObj)
        binding.gameChooserModel = areaViewModel
    }

}