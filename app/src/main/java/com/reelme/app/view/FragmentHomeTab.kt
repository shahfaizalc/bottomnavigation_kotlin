package com.reelme.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reelme.app.adapter.ViewPagerAdapter
import com.reelme.app.util.Constants
import com.reelme.app.viewmodel.HomeTabViewModel
import com.reelme.app.R
import com.reelme.app.databinding.ActivityHometabBinding
import com.reelme.app.fragments.BaseFragment

class FragmentHomeTab : BaseFragment() {

    internal var binding: ActivityHometabBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = ActivityHometabBinding.inflate(inflater, container, false)
            val areaViewModel = HomeTabViewModel()
            binding!!.homeData = areaViewModel
            binding!!.homeData!!.setPagerAdapter(adapter)
        }
        return binding!!.root
    }

    val adapter: ViewPagerAdapter
        get() {
            val adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
            val number = resources.getStringArray(R.array.tabs)
            for (i in number.indices) {
                adapter.addFrag(Constants.SALE_FRAGMENTS[i] as Fragment, number[i])
            }
            return adapter
        }
}
