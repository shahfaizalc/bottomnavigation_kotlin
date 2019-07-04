package com.faizal.bottomnavigation.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 */

open class BaseFragment : Fragment() {


    lateinit  var mFragmentNavigation: FragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
    }

    companion object {

        val ARGS_INSTANCE = "com.faizal.bottomnavigation"
    }


}
