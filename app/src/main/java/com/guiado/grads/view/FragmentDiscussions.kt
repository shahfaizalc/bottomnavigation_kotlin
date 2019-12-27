package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentDiscussionBinding
import com.guiado.grads.databinding.ContentMyadsBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.DiscussionModel


class FragmentDiscussions : BaseFragment() {


    var binding: ContentDiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentDiscussionBinding>(inflater, R.layout.content_discussion, container, false)
            val areaViewModel = DiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
