package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentJoingroupBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.utils.Constants
import com.guiado.grads.viewmodel.JoinGroupViewModel


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
