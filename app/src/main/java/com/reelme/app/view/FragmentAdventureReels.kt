package com.reelme.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R

import com.reelme.app.databinding.*
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.viewmodel.*


class FragmentAdventureReels : AppCompatActivity() {


    @Transient
    lateinit internal var areaViewModel: ChooseYourAdventuresModel

    /**
     * Binding
     */
  //  internal var binding: ContentChooseyouradventureBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ContentChooseyouradventureBinding = DataBindingUtil.setContentView(this, R.layout.content_chooseyouradventure)
        areaViewModel = ChooseYourAdventuresModel(this)
        binding.adSearchModel = areaViewModel
    }

//
//    private fun setBindingAttributes(areaViewModel: MyFollowModel) {
//        binding!!.adSearchModel = areaViewModel
//
//    }


    override fun onResume() {
        super.onResume()
      //  areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
      //  areaViewModel.unRegisterListeners()
    }
}

