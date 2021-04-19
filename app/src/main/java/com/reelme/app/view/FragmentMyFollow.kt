package com.reelme.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reelme.app.databinding.*
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.viewmodel.*


class FragmentMyFollow : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: MyFollowingModel

    /**
     * Binding
     */
    internal var binding: ContentMyfollowBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = ContentMyfollowBinding.inflate(inflater, container, false)
            val areaViewModel = MyFollowingModel(this.activity!!)
            binding!!.adSearchModel = areaViewModel
        }

        return binding!!.root
    }


    private fun setBindingAttributes(areaViewModel: MyFollowingModel) {
        binding!!.adSearchModel = areaViewModel

    }


    override fun onResume() {
        super.onResume()
      //  areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
      //  areaViewModel.unRegisterListeners()
    }
}

