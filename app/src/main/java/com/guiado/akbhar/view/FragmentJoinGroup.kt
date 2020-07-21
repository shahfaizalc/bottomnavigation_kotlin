package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentJoingroupBinding
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.viewmodel.JoinGroupViewModel


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
