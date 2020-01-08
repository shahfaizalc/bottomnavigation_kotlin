package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ContentRequestcompleteBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.model2.Profile
import com.guiado.linkify.model2.Reviews
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.viewmodel.RequestCompleteViewModel

class FragmentRequestComplete : BaseFragment() {


    lateinit var binding :ContentRequestcompleteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)
        val AD_DOCID = arguments!!.getString(Constants.AD_DOCID);
        return bindView(inflater, container, postAdObj!!,AD_DOCID)
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
        binding.mainDataModel?.userIds?.observe(this, object: Observer<List<Reviews>>{
            override fun onChanged(t: List<Reviews>?) {

                if (t != null) {
                    binding.mainDataModel?.adapter?.setData(t)
                }
            }
        } )
    }
}


