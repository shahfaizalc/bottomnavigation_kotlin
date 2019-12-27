package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.ContentDiscussionBinding
import com.faizal.guiado.databinding.ContentMyadsBinding
import com.faizal.guiado.databinding.ContentMydiscussionBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.MyDiscussionModel


class FragmentMyDiscussions : BaseFragment() {


    var binding: ContentMydiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentMydiscussionBinding>(inflater, R.layout.content_mydiscussion, container, false)
            val areaViewModel = MyDiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
