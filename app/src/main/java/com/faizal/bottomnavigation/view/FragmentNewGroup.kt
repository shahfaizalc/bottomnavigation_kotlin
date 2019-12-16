package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentEventsBinding
import com.faizal.bottomnavigation.databinding.FragmentNewdiscussionBinding
import com.faizal.bottomnavigation.databinding.FragmentNewgroupBinding
import com.faizal.bottomnavigation.databinding.FragmentProfileeditBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.EventsViewModel
import com.faizal.bottomnavigation.viewmodel.NewDiscussionViewModel
import com.faizal.bottomnavigation.viewmodel.NewGroupViewModel
import com.faizal.bottomnavigation.viewmodel.ProfileEditViewModel


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
