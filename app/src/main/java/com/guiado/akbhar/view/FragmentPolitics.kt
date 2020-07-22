package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ContentDiscussionBinding
import com.guiado.akbhar.databinding.ContentPoliticsBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.PoliticsViewModel


class  FragmentPolitics : BaseFragment() {

    @Transient
    var binding: ContentPoliticsBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentPoliticsBinding>(inflater, R.layout.content_politics, container, false)
            val areaViewModel = PoliticsViewModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }

    override fun onDestroy() {
//        adView.destroy()
        super.onDestroy()
    }


}
