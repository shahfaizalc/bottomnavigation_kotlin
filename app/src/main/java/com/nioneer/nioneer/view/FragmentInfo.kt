package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentFindrideBinding
import com.nioneer.nioneer.databinding.FragmentInfoBinding
import com.nioneer.nioneer.viewmodel.InfoViewModel


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
