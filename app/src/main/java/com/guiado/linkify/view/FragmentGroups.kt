package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentGroupsBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.GroupsModel


class FragmentGroups : Activity() {


    var binding: ContentGroupsBinding? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_groups)
            val areaViewModel = GroupsModel(this, this)
            binding?.adSearchModel = areaViewModel
        }
    }

}
