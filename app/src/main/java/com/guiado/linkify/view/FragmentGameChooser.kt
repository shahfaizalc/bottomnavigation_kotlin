package com.guiado.linkify.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentGamechooserBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.GameChooserModel

class FragmentGameChooser : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        val binding : FragmentGamechooserBinding = DataBindingUtil.setContentView(this, R.layout.fragment_gamechooser)
        val areaViewModel = GameChooserModel( this,postAdObj)
        binding.gameChooserModel = areaViewModel
    }

}