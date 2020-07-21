package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentHomepageBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.HomePageModel


class FragmentHomePage : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ContentHomepageBinding>(inflater, R.layout.content_homepage, container, false)
        val areaViewModel = HomePageModel(activity!!, this)
        binding.homePageModel = areaViewModel
        return binding.root
    }

}
