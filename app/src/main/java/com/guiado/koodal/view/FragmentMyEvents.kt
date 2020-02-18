package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentGroupsBinding
import com.guiado.koodal.databinding.ContentMyeventsBinding
import com.guiado.koodal.databinding.ContentTheeventsBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.MyEventsModel


class FragmentMyEvents : BaseFragment() {


    var binding: ContentMyeventsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_myevents, container, false)
            val areaViewModel = MyEventsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
