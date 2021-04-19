package com.reelme.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.reelme.app.R
import com.reelme.app.databinding.ContentFollowBinding
import com.reelme.app.databinding.ContentGoalBinding
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel


class FragmentFollow : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: FollowModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ContentFollowBinding = DataBindingUtil.setContentView(this, R.layout.content_follow)
        areaViewModel = FollowModel(this, this)
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

