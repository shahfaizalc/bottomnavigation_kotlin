package com.guiado.akbhar.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentArtBinding
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.ArtViewModel
import com.guiado.akbhar.viewmodel.DiscussionModel


class  FragmentArt :  AppCompatActivity() {

    @Transient
    var binding: ContentArtBinding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     if (binding == null) {
         binding = DataBindingUtil.setContentView(this, R.layout.content_art);

            val areaViewModel = ArtViewModel(this, this)
            binding?.adSearchModel = areaViewModel
        }
    }

}
