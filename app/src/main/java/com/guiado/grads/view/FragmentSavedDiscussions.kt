package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentDiscussionBinding
import com.guiado.grads.databinding.ContentMyadsBinding
import com.guiado.grads.databinding.ContentMydiscussionBinding
import com.guiado.grads.databinding.ContentSaveddiscussionBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.MyDiscussionModel
import com.guiado.grads.viewmodel.SavedDiscussionModel


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
