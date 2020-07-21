package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentSavedeventsBinding
import com.guiado.akbhar.viewmodel.SavedEventsModel


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
