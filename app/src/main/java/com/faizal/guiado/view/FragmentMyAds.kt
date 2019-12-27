package com.faizal.guiado.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.ContentMyadsBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.viewmodel.MyAdsModel


class FragmentMyAds : BaseFragment() {


    var binding: ContentMyadsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentMyadsBinding>(inflater, R.layout.content_myads, container, false)
            val areaViewModel = MyAdsModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
