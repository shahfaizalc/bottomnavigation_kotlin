package com.guiado.linkify.view

import android.app.Activity
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


class FragmentSavedDiscussions : Activity() {


    var binding: ContentSaveddiscussionBinding? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_saveddiscussion)
            val areaViewModel = SavedDiscussionModel( this)
            binding?.adSearchModel = areaViewModel
        }
    }

}
