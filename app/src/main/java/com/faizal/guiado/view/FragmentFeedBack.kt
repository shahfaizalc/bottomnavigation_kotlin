package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentFeedbackBinding
import com.faizal.guiado.databinding.FragmentProfileeditBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.FeedbackViewModel


class FragmentFeedBack : BaseFragment() {

    var binding: FragmentFeedbackBinding? = null

    @Transient
    lateinit internal var areaViewModel: FeedbackViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false)
            areaViewModel = FeedbackViewModel(this.context!!, this)
            binding!!.homeData = areaViewModel
        }
        return binding!!.root
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
