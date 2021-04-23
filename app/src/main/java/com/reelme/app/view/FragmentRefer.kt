package com.reelme.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.reelme.app.R
import com.reelme.app.databinding.ContentFollowBinding
import com.reelme.app.databinding.ContentGoalBinding
import com.reelme.app.databinding.ContentReferBinding
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.ReferModel


class FragmentRefer : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReferModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ContentReferBinding = DataBindingUtil.setContentView(this, R.layout.content_refer)
        areaViewModel = ReferModel(this, this)
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

