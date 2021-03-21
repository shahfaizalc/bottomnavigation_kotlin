package com.reelme.realme.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.reelme.realme.R
import com.reelme.realme.databinding.ContentChallengeBinding
import com.reelme.realme.fragments.BaseFragment
import com.reelme.realme.viewmodel.ChallengeModel


class FragmentChallenges : BaseFragment() {

    var binding: ContentChallengeBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentChallengeBinding>(inflater, R.layout.content_challenge, container, false)
            val areaViewModel = ChallengeModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }


    override fun onResume() {
        Log.d("on Resume","on Resuem called")
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}
