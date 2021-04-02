package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentNewdiscussionBinding
import com.reelme.app.viewmodel.NewDiscussionViewModel


class FragmentNewDiscusssion : AppCompatActivity() {

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
