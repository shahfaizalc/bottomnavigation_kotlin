package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentDiscussionBinding
import com.faizal.bottomnavigation.databinding.ContentMyadsBinding
import com.faizal.bottomnavigation.databinding.ContentMydiscussionBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.DiscussionModel
import com.faizal.bottomnavigation.viewmodel.MyAdsModel
import com.faizal.bottomnavigation.viewmodel.MyDiscussionModel


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
