package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentGroupsBinding
import com.guiado.grads.databinding.ContentMygroupsBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.MyGroupsModel


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
