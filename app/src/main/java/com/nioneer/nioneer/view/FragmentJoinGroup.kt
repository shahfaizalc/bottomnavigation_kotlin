package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentJoingroupBinding
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.viewmodel.JoinGroupViewModel


class FragmentJoinGroup : Activity() {

    @Transient
    lateinit internal var areaViewModel: JoinGroupViewModel

    lateinit var binding: FragmentJoingroupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        val postAdObj = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_joingroup)
        areaViewModel = JoinGroupViewModel(this, postAdObj!!)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.postDiscussion
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
