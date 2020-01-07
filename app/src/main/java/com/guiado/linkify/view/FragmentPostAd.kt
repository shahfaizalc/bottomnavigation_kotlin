package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentPostadBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.model.PostAdModel
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.PostAdViewModel


class FragmentPostAd : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getParcelable<PostAdModel>(Constants.POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: PostAdModel): View {
        val binding = DataBindingUtil.inflate<ContentPostadBinding>(inflater, R.layout.content_postad, container, false)
        val areaViewModel = PostAdViewModel(activity!!, this,postAdObj)
        binding.postAdViewData = areaViewModel
        return binding.root
    }

}
