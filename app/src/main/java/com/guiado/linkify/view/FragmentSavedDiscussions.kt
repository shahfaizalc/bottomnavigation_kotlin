package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentDiscussionBinding
import com.guiado.linkify.databinding.ContentMyadsBinding
import com.guiado.linkify.databinding.ContentMydiscussionBinding
import com.guiado.linkify.databinding.ContentSaveddiscussionBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.SavedDiscussionModel


class FragmentSavedDiscussions : BaseFragment() {


    var binding: ContentSaveddiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_saveddiscussion, container, false)
            val areaViewModel = SavedDiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
