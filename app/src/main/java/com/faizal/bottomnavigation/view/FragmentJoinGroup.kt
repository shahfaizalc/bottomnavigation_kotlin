package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentJoingroupBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model2.Comments
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.JoinGroupViewModel


class FragmentJoinGroup : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: JoinGroupViewModel

    lateinit var binding : FragmentJoingroupBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)

        return bindView(inflater, container, postAdObj!!)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joingroup, container, false)
        areaViewModel = JoinGroupViewModel(this.context!!, this,postAdObj)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.postDiscussion
        return binding.root
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
