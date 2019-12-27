package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.FragmentEventsBinding
import com.guiado.grads.databinding.FragmentNewdiscussionBinding
import com.guiado.grads.databinding.FragmentNewgroupBinding
import com.guiado.grads.databinding.FragmentProfileeditBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.NewGroupViewModel


class FragmentNewGroup : BaseFragment() {

    var binding: FragmentNewgroupBinding? = null

    @Transient
    lateinit internal var areaViewModel: NewGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentNewgroupBinding>(inflater, R.layout.fragment_newgroup, container, false)
            areaViewModel = NewGroupViewModel(this.context!!, this)
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
