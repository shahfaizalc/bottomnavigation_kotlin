package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentFeedbackBinding
import com.guiado.akbhar.databinding.FragmentProfileeditBinding
import com.guiado.akbhar.viewmodel.FeedbackViewModel


class FragmentFeedBack : Activity() {

    var binding: FragmentFeedbackBinding? = null

    @Transient
    lateinit internal var areaViewModel: FeedbackViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_feedback)
            areaViewModel = FeedbackViewModel(this, this)
            binding!!.homeData = areaViewModel
        }
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
