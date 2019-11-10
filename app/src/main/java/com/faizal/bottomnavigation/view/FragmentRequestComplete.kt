package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentRequestcompleteBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.PostRequestViewModel


class FragmentRequestComplete : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val postAdObj = arguments!!.getParcelable<PostAdModel>(Constants.POSTAD_OBJECT)
        return bindView(inflater, container, postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: PostAdModel?): View {
        val binding = DataBindingUtil.inflate<ContentRequestcompleteBinding>(inflater, R.layout.content_requestcomplete, container, false)
        val areaViewModel = PostRequestViewModel(activity!!, this, postAdObj)
        binding.postCompleteData = areaViewModel
        return binding.root
    }

}
