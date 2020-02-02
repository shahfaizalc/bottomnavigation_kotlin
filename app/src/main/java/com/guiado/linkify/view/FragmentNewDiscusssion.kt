package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentEventsBinding
import com.guiado.linkify.databinding.FragmentNewdiscussionBinding
import com.guiado.linkify.databinding.FragmentProfileeditBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.NewDiscussionViewModel


class FragmentNewDiscusssion : BaseFragment() {

    var binding: FragmentNewdiscussionBinding? = null

    @Transient
    lateinit internal var areaViewModel: NewDiscussionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentNewdiscussionBinding>(inflater, R.layout.fragment_newdiscussion, container, false)
            areaViewModel = NewDiscussionViewModel(this.context!!, this)
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
