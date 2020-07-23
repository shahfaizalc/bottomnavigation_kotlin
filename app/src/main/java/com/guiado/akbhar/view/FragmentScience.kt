package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentArtBinding
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.databinding.ContentScienceBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.ArtViewModel
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.ScienceViewModel


class  FragmentScience :  AppCompatActivity() {

        @Transient
        var binding: ContentScienceBinding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (binding == null) {
                binding = DataBindingUtil.setContentView(this, R.layout.content_science);

                val areaViewModel = ScienceViewModel(this, this)
                binding?.adSearchModel = areaViewModel
            }
        }

    }
