package com.guiado.akbhar.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentGroupsBinding
import com.guiado.akbhar.databinding.ContentMyeventsBinding
import com.guiado.akbhar.databinding.ContentTheeventsBinding
import com.guiado.akbhar.viewmodel.MyEventsModel


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
