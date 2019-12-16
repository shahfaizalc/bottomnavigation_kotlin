package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentGroupsBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.GroupsModel


class FragmentGroups : BaseFragment() {


    var binding: ContentGroupsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_groups, container, false)
            val areaViewModel = GroupsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
