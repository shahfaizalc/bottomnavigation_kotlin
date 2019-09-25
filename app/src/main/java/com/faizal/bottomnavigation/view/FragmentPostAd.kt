package com.faizal.bottomnavigation.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentPostadBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.PostAdViewModel


class FragmentPostAd : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ContentPostadBinding>(inflater, R.layout.content_postad, container, false)
        val areaViewModel = PostAdViewModel(activity!!, this)
        binding.postAdViewData = areaViewModel
        return binding.root
    }

}
