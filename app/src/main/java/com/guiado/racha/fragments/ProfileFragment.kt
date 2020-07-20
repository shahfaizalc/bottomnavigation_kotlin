package com.guiado.racha.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.guiado.racha.R
import com.guiado.racha.activities.MainActivity


class ProfileFragment : BaseFragment() {


    internal var btnClickMe: Button? = null

    internal var fragCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment_sports

        val view = inflater.inflate(R.layout.fragment_profile, container, false)



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
                mFragmentNavigation.pushFragment(ProfileFragment.newInstance(fragCount + 1))

            }
        }


        (activity as MainActivity).updateToolbarTitle(if (fragCount == 0) "Home" else "Child Home $fragCount")

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {


        fun newInstance(instance: Int): ProfileFragment {
            val args = Bundle()
            args.putInt(BaseFragment.Companion.ARGS_INSTANCE, instance)
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
