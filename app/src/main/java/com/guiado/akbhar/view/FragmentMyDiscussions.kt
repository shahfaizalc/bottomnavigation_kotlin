package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.databinding.ContentMyadsBinding
import com.guiado.akbhar.databinding.ContentMydiscussionBinding
import com.guiado.akbhar.viewmodel.MyDiscussionModel


class FragmentMyDiscussions : Activity() {


    var binding: ContentMydiscussionBinding? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.content_mydiscussion)
            val areaViewModel = MyDiscussionModel(this, this)
            binding?.adSearchModel = areaViewModel
        }
    }

}
