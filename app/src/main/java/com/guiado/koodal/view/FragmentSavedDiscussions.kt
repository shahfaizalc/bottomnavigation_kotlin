package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentDiscussionBinding
import com.guiado.koodal.databinding.ContentMyadsBinding
import com.guiado.koodal.databinding.ContentMydiscussionBinding
import com.guiado.koodal.databinding.ContentSaveddiscussionBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.SavedDiscussionModel


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
