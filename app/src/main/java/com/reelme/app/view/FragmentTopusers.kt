package com.reelme.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.reelme.app.R
import com.reelme.app.databinding.ContentFollowBinding
import com.reelme.app.databinding.ContentGoalBinding
import com.reelme.app.databinding.ContentTopusersBinding
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.TopusersModel


class FragmentTopusers : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: TopusersModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ContentTopusersBinding = DataBindingUtil.setContentView(this, R.layout.content_topusers)
        areaViewModel = TopusersModel(this, this)
        binding.adSearchModel = areaViewModel
    }

    override fun onResume() {
        super.onResume()
      //  areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
      //  areaViewModel.unRegisterListeners()
    }
}

