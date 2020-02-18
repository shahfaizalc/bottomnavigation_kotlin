package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentAdsearchBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.AdSearchModel


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
