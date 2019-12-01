package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentAdsearchBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.AdSearchModel


class FragmentAdSearch : BaseFragment() {


     var binding: ContentAdsearchBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentAdsearchBinding>(inflater, R.layout.content_adsearch, container, false)
            val areaViewModel = AdSearchModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
