package com.guiado.akbhar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentHomeBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private val tabIcons = intArrayOf(R.drawable.tab_home)

    @Transient
    var binding: FragmentHomeBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
            val areaViewModel = HomeViewModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
            addTabs( binding!!.viewpager);
            binding!!.tabs.setupWithViewPager( binding!!.viewpager);
        }

        return binding!!.root
    }

    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(activity!!.getSupportFragmentManager())
        adapter.addFrag(FragmentDiscussions(), "Morocco")
        adapter.addFrag(FragmentDiscussions(), "World")
        adapter.addFrag(FragmentDiscussions(), "Sports")
        adapter.addFrag(FragmentDiscussions(), "Business")
        adapter.addFrag(FragmentDiscussions(), "Politics")
        adapter.addFrag(FragmentDiscussions(), "Entertainment")

        viewPager.adapter = adapter
    }

    //note: tablayout icons hidden
    private fun setupTabIcons() {
//        tabLayout!!.getTabAt(0)!!.setIcon(tabIcons[0])
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

}
