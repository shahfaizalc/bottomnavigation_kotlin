package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.FragmentEventsBinding
import com.guiado.koodal.databinding.FragmentNewdiscussionBinding
import com.guiado.koodal.databinding.FragmentNewgroupBinding
import com.guiado.koodal.databinding.FragmentProfileeditBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.NewGroupViewModel


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
