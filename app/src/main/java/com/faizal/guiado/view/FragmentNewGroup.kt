package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentEventsBinding
import com.faizal.guiado.databinding.FragmentNewdiscussionBinding
import com.faizal.guiado.databinding.FragmentNewgroupBinding
import com.faizal.guiado.databinding.FragmentProfileeditBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.NewGroupViewModel


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
