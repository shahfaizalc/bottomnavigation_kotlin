package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentHomepageBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.HomePageModel


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
