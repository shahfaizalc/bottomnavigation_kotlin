package com.nioneer.nioneer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ContentMyadsBinding
import com.nioneer.nioneer.databinding.ContentSimilaradsBinding
import com.nioneer.nioneer.fragments.BaseFragment
import com.nioneer.nioneer.viewmodel.SimilarDiscussionModel


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
