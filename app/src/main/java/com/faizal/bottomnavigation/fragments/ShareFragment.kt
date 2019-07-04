package com.faizal.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.activities.MainActivity



class ShareFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_share, container, false)


        (activity as MainActivity).updateToolbarTitle("Share")


        return view
    }


}
