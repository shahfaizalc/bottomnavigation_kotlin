package com.nioneer.nioneer.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentGroupsBinding
import com.nioneer.nioneer.databinding.ContentSavedeventsBinding
import com.nioneer.nioneer.databinding.ContentTheeventsBinding
import com.nioneer.nioneer.viewmodel.SavedEventsModel


class FragmentSavedEvents : Activity() {


    var binding: ContentSavedeventsBinding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_savedevents)
            val areaViewModel = SavedEventsModel( this)
            binding?.adSearchModel = areaViewModel
        }

    }

}
