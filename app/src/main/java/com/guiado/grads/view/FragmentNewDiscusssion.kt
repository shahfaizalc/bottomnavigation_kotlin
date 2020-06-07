package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentEventsBinding
import com.guiado.grads.databinding.FragmentNewdiscussionBinding
import com.guiado.grads.databinding.FragmentProfileeditBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.NewDiscussionViewModel


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
