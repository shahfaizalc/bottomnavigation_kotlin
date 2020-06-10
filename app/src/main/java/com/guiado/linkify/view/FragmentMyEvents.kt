package com.guiado.linkify.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentGroupsBinding
import com.guiado.linkify.databinding.ContentMyeventsBinding
import com.guiado.linkify.databinding.ContentTheeventsBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.MyEventsModel


class FragmentMyEvents : Activity() {


    var binding: ContentMyeventsBinding? = null;
    lateinit var areaViewModel : MyEventsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_myevents)
            areaViewModel = MyEventsModel( this)
            binding?.adSearchModel = areaViewModel
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // areaViewModel.doGetTalents();

    }

}
