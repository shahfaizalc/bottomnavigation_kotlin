package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentGroupsBinding
import com.guiado.linkify.databinding.ContentMyeventsBinding
import com.guiado.linkify.databinding.ContentSavedeventsBinding
import com.guiado.linkify.databinding.ContentTheeventsBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.SavedEventsModel


class FragmentSavedEvents : BaseFragment() {


    var binding: ContentSavedeventsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_savedevents, container, false)
            val areaViewModel = SavedEventsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
