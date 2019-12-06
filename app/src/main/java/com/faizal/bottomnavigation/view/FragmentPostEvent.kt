package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentPostEventBinding
import com.faizal.bottomnavigation.databinding.ContentPostadBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.PostAdViewModel
import com.faizal.bottomnavigation.viewmodel.PostEventViewModel


class FragmentPostEvent : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getParcelable<PostAdModel>(Constants.POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: PostAdModel): View {
        val binding = DataBindingUtil.inflate<ContentPostEventBinding>(inflater, R.layout.content_post_event, container, false)
        val areaViewModel = PostEventViewModel(activity!!, this,postAdObj)
        binding.postAdViewData = areaViewModel
        return binding.root
    }

}
