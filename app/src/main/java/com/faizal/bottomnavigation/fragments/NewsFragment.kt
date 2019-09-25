package com.faizal.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.activities.MainActivity


class NewsFragment : BaseFragment() {


    internal var btnClickMe: Button? = null

    internal var fragCount: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_news, container, false)

        btnClickMe = view.findViewById<Button>(R.id.btn_click_me)

        val args = arguments
        if (args != null) {
            fragCount = args.getInt(BaseFragment.Companion.ARGS_INSTANCE)
        }



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnClickMe!!.setOnClickListener {
            if (mFragmentNavigation != null) {
               // mFragmentNavigation.pushFragment(NewsFragment.newInstance(fragCount + 1), bundle)


            }
        }


        (activity as MainActivity).updateToolbarTitle(if (fragCount == 0) "News" else "Sub News $fragCount")


    }

    companion object {


        fun newInstance(instance: Int): NewsFragment {
            val args = Bundle()
            args.putInt(BaseFragment.Companion.ARGS_INSTANCE, instance)
            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
