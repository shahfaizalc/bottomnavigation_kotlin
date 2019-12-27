package com.guiado.grads.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.grads.R
import com.guiado.grads.databinding.ContentPostadBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.model.PostAdModel
import com.guiado.grads.utils.Constants
import com.guiado.grads.viewmodel.PostAdViewModel


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
