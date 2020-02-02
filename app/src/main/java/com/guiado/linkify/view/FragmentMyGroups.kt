package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentGroupsBinding
import com.guiado.linkify.databinding.ContentMygroupsBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.MyGroupsModel


class FragmentMyGroups : BaseFragment() {


    var binding: ContentMygroupsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_mygroups, container, false)
            val areaViewModel = MyGroupsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
