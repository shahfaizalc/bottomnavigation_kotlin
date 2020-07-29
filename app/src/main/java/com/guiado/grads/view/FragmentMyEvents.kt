package com.guiado.grads.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentGroupsBinding
import com.guiado.grads.databinding.ContentMyeventsBinding
import com.guiado.grads.databinding.ContentTheeventsBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.MyEventsModel
import com.guiado.grads.viewmodel.TheEventsModel


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