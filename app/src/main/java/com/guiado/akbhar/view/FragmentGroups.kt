package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentGroupsBinding
import com.guiado.akbhar.viewmodel.GroupsModel


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