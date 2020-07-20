package com.guiado.racha.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.guiado.racha.R
import com.guiado.racha.activities.MainActivity



class SearchFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment_sports

        val view = inflater.inflate(R.layout.fragment_search, container, false)


        (activity as MainActivity).updateToolbarTitle("Search")


        return view
    }


}
