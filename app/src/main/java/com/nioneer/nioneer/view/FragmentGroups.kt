package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentGroupsBinding
import com.nioneer.nioneer.viewmodel.GroupsModel


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
