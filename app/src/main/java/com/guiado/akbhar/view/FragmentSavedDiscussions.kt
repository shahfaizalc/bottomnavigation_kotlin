package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.databinding.ContentMyadsBinding
import com.guiado.akbhar.databinding.ContentMydiscussionBinding
import com.guiado.akbhar.databinding.ContentSaveddiscussionBinding
import com.guiado.akbhar.viewmodel.SavedDiscussionModel


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
