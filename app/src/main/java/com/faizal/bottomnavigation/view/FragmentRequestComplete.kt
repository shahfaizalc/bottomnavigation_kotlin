package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ContentRequestcompleteBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.RequestCompleteViewModel

class FragmentRequestComplete : BaseFragment() {


    lateinit var binding :ContentRequestcompleteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)
        val AD_DOCID = arguments!!.getString(Constants.AD_DOCID);
        return bindView(inflater, container, postAdObj,AD_DOCID)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String, adDocid: String?): View {
        binding = DataBindingUtil.inflate<ContentRequestcompleteBinding>(inflater, R.layout.content_requestcomplete, container, false)
        val areaViewModel = RequestCompleteViewModel(activity!!, this, postAdObj,adDocid)
        binding.mainDataModel = areaViewModel
        binding.profile = Profile()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profile = binding.mainDataModel!!.profile

    }
}
