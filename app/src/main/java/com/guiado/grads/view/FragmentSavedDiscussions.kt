package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentSaveddiscussionBinding
import com.guiado.grads.viewmodel.SavedDiscussionModel


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
