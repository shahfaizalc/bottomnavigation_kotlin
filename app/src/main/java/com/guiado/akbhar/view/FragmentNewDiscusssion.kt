package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentNewdiscussionBinding
import com.guiado.akbhar.viewmodel.NewDiscussionViewModel


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