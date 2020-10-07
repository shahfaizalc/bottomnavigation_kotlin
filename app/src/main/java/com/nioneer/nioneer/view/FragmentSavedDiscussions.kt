package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentDiscussionBinding
import com.nioneer.nioneer.databinding.ContentMyadsBinding
import com.nioneer.nioneer.databinding.ContentMydiscussionBinding
import com.nioneer.nioneer.databinding.ContentSaveddiscussionBinding
import com.nioneer.nioneer.viewmodel.SavedDiscussionModel


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
