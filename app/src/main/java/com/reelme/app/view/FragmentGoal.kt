package com.reelme.app.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.reelme.app.R
import com.reelme.app.databinding.ContentGoalBinding
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.viewmodel.GoalModel


class FragmentGoal : BaseFragment() {

    var binding: ContentGoalBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.content_goal, container, false)
            val areaViewModel = GoalModel(activity!!, this)
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
