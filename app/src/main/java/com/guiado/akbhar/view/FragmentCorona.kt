package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentCoronaBinding
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.CoronaModel
import com.guiado.akbhar.viewmodel.DiscussionModel


class FragmentCorona : BaseFragment() {

    @Transient
    var binding: ContentCoronaBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentCoronaBinding>(inflater, R.layout.content_corona, container, false)
            val areaViewModel = CoronaModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

    override fun onDestroy() {
//        adView.destroy()
        super.onDestroy()
    }


}
