package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentGroupsBinding
import com.guiado.koodal.databinding.ContentTheeventsBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.TheEventsModel


class FragmentTheEvents : BaseFragment() {


    var binding: ContentTheeventsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_theevents, container, false)
            val areaViewModel = TheEventsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
