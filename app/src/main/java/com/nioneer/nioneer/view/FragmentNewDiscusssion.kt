package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.FragmentNewdiscussionBinding
import com.nioneer.nioneer.databinding.FragmentProfileeditBinding
import com.nioneer.nioneer.viewmodel.NewDiscussionViewModel


class FragmentNewDiscusssion : Activity() {

    var binding: FragmentNewdiscussionBinding? = null

    @Transient
    lateinit internal var areaViewModel: NewDiscussionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_newdiscussion)
            areaViewModel = NewDiscussionViewModel(this, this)
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
