package com.guiado.koodal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.koodal.R
import com.guiado.koodal.databinding.ContentDiscussionBinding
import com.guiado.koodal.databinding.ContentMyadsBinding
import com.guiado.koodal.databinding.ContentMydiscussionBinding
import com.guiado.koodal.fragments.BaseFragment
import com.guiado.koodal.viewmodel.MyDiscussionModel


class FragmentMyDiscussions : BaseFragment() {


    var binding: ContentMydiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentMydiscussionBinding>(inflater, R.layout.content_mydiscussion, container, false)
            val areaViewModel = MyDiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

}
