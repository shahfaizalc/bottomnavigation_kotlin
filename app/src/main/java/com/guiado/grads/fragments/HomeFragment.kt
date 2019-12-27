package com.guiado.grads.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.guiado.grads.R
import com.guiado.grads.activities.Main2Activity


class HomeFragment : BaseFragment() {


    internal var btnClickMe: Button? = null

    internal var fragCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)



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
           // mFragmentNavigation.pushFragment(newInstance(fragCount + 1, this, bundle))
        }


        (activity as Main2Activity).updateToolbarTitle(if (fragCount == 0) "Home" else "Child Home $fragCount")

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}// Required empty public constructor
