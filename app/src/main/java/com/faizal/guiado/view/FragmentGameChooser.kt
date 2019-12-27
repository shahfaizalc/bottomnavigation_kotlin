package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentGamechooserBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.utils.Constants
import com.faizal.guiado.viewmodel.GameChooserModel

class FragmentGameChooser : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String?): View {
        val binding = DataBindingUtil.inflate<FragmentGamechooserBinding>(inflater, R.layout.fragment_gamechooser, container, false)
        val areaViewModel = GameChooserModel(activity!!, this,postAdObj)
        binding.gameChooserModel = areaViewModel
        return binding.root
    }

}