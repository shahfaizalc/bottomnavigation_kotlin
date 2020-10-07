package com.nioneer.nioneer.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentGroupsBinding
import com.nioneer.nioneer.databinding.ContentMyeventsBinding
import com.nioneer.nioneer.databinding.ContentTheeventsBinding
import com.nioneer.nioneer.viewmodel.MyEventsModel


class FragmentMyEvents : Activity() {


    var binding: ContentMyeventsBinding? = null;
    lateinit var areaViewModel : MyEventsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_myevents)
            areaViewModel = MyEventsModel( this)
            binding?.adSearchModel = areaViewModel
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // areaViewModel.doGetTalents();

    }

}
