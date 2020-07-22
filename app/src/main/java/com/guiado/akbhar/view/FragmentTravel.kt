package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.databinding.ContentTravelBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.TravelViewModel


class  FragmentTravel : BaseFragment() {

    @Transient
    var binding: ContentTravelBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentTravelBinding>(inflater, R.layout.content_travel, container, false)
            val areaViewModel = TravelViewModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

    override fun onDestroy() {
//        adView.destroy()
        super.onDestroy()
    }


}
