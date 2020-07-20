package com.guiado.racha.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.guiado.racha.R
import com.guiado.racha.activities.MainActivity


class HomeFragment : BaseFragment() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private val tabIcons = intArrayOf(
            R.drawable.tab_home,
            R.drawable.tab_home,
            R.drawable.tab_home,
            R.drawable.tab_home,
            R.drawable.tab_home,
            R.drawable.tab_home

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment_sports

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        (activity as MainActivity).updateToolbarTitle("Profile")


        viewPager = view!!.findViewById(R.id.viewpager);
        addTabs(viewPager!!);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout!!.setupWithViewPager(viewPager);
       // setupTabIcons();


        return view
    }

    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(activity!!.getSupportFragmentManager())
        adapter.addFrag(MoroccoFragment(), "Morocco")
        adapter.addFrag(WorldFragment(), "World")
        adapter.addFrag(BusinessFragment(), "Business")
        adapter.addFrag(PoliticsFragment(), "Politics")
        adapter.addFrag(SportsFragment(), "Sports")
        adapter.addFrag(EntertainmentFragment(), "Entertainment")
        adapter.addFrag(TravelFragment(),"Travel")

        viewPager.adapter = adapter
    }

    //note: tablayout icons hidden
    private fun setupTabIcons() {
        tabLayout!!.getTabAt(0)!!.setIcon(tabIcons[0])
        tabLayout!!.getTabAt(1)!!.setIcon(tabIcons[1])
        tabLayout!!.getTabAt(2)!!.setIcon(tabIcons[2])
        tabLayout!!.getTabAt(3)!!.setIcon(tabIcons[3])
        tabLayout!!.getTabAt(4)!!.setIcon(tabIcons[4])
        tabLayout!!.getTabAt(5)!!.setIcon(tabIcons[5])
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
