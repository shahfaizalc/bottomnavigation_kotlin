package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentProfileeditBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.ProfileEditViewModel


class FragmentProfileEdit : BaseFragment() {

    var binding: FragmentProfileeditBinding? = null

    @Transient
    lateinit internal var areaViewModel: ProfileEditViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)

        return bindView(inflater, container,postAdObj!!)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentProfileeditBinding>(inflater, R.layout.fragment_profileedit, container, false)
            areaViewModel = ProfileEditViewModel(this.context!!, this,postAdObj)
            binding!!.homeData = areaViewModel
            binding!!.profile = areaViewModel.profile
        }
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
