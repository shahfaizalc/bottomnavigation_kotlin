package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentFindrideBinding
import com.guiado.linkify.databinding.FragmentInfoBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.InfoViewModel


class FragmentInfo : Activity() {

    @Transient
    lateinit internal var areaViewModel: InfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      val binding : FragmentInfoBinding= DataBindingUtil.setContentView(this, R.layout.fragment_info)
        areaViewModel = InfoViewModel( this)
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

//    fun showBottom(){
//
//        (activity as Main2Activity).bottomLayout(View.VISIBLE)
//
//    }
}
