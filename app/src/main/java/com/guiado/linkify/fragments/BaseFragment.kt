package com.guiado.linkify.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 */

open class BaseFragment : Fragment() {


    lateinit  var mFragmentNavigation: FragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }

    interface FragmentNavigation {
        fun popFragment(depth: Int)
        fun pushFragment(fragment: Fragment)
        fun replaceFragment(fragment: Fragment)
        fun switchTab(position : Int)
        fun viewBottom(viewState : Int)
        fun viewToolbar(b: Boolean)
    }

    companion object {

        val ARGS_INSTANCE = "com.guiado.linkify"
    }

    fun newInstance(instance: Int, fragment: BaseFragment, bundle: Bundle): BaseFragment {
        val args = bundle
        args.putInt(ARGS_INSTANCE, instance)
        fragment.arguments = args
        return fragment
    }



}