package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentMyadsBinding
import com.guiado.grads.databinding.ContentSimilaradsBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.SimilarDiscussionModel


class FragmentSimiliarDiscussion : BaseFragment() {


    var binding: ContentSimilaradsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentSimilaradsBinding>(inflater, R.layout.content_similarads, container, false)
            val areaViewModel = SimilarDiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
