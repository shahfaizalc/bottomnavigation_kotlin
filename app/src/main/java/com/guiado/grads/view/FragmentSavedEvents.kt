package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentGroupsBinding
import com.guiado.grads.databinding.ContentMyeventsBinding
import com.guiado.grads.databinding.ContentSavedeventsBinding
import com.guiado.grads.databinding.ContentTheeventsBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.MyEventsModel
import com.guiado.grads.viewmodel.SavedEventsModel
import com.guiado.grads.viewmodel.TheEventsModel


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
