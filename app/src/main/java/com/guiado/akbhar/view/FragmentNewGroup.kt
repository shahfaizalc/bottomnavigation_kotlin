package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentEventsBinding
import com.guiado.akbhar.databinding.FragmentNewdiscussionBinding
import com.guiado.akbhar.databinding.FragmentNewgroupBinding
import com.guiado.akbhar.databinding.FragmentProfileeditBinding
import com.guiado.akbhar.viewmodel.NewGroupViewModel


class FragmentNewGroup : Activity() {

    var binding: FragmentNewgroupBinding? = null

    @Transient
    lateinit internal var areaViewModel: NewGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.fragment_newgroup)
            areaViewModel = NewGroupViewModel(this, this)
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
