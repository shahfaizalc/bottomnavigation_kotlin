package com.faizal.bottomnavigation.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView

import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.fragments.NewsFragment
import com.faizal.bottomnavigation.fragments.HomeFragment
import com.faizal.bottomnavigation.fragments.ShareFragment
import com.faizal.bottomnavigation.fragments.ProfileFragment
import com.faizal.bottomnavigation.fragments.SearchFragment
import com.faizal.bottomnavigation.utils.FragmentHistory
import com.faizal.bottomnavigation.utils.Utils
import com.faizal.bottomnavigation.views.FragNavController

import com.faizal.bottomnavigation.R

class MainActivity : BaseActivity(), BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {


    internal var contentFrame: FrameLayout? = null

    internal var toolbar: Toolbar? = null

    private val mTabIconsSelected = intArrayOf(R.drawable.tab_home, R.drawable.tab_search, R.drawable.tab_share, R.drawable.tab_news, R.drawable.tab_profile)


    internal lateinit var TABS: ArrayList<String>

    internal var bottomTabLayout: TabLayout? = null

    private var mNavController: FragNavController? = null

    private var fragmentHistory: FragmentHistory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bottomTabLayout = findViewById(R.id.bottom_tab_layout)
        TABS = ArrayList();
        TABS.add("Home")
        TABS.add("Search")
        TABS.add("Share")
        TABS.add("News")
        TABS.add("Profile")

        toolbar = findViewById(R.id.toolbar)

        initToolbar()

        initTab()

        fragmentHistory = FragmentHistory()


        mNavController = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.size)
                .build()


        switchTab(0)

        bottomTabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                fragmentHistory!!.push(tab.position)

                switchTab(tab.position)


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                mNavController!!.clearStack()

                switchTab(tab.position)


            }
        })

    }

    private fun initToolbar() {

        setSupportActionBar(toolbar)


    }

    private fun initTab() {
        if (bottomTabLayout != null) {
            for (i in TABS.indices) {
                bottomTabLayout!!.addTab(bottomTabLayout!!.newTab())
                val tab = bottomTabLayout!!.getTabAt(i)
                if (tab != null)
                    tab.customView = getTabView(i)
            }
        }
    }


    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.tab_item_bottom, null)
        val icon = view.findViewById(R.id.tab_icon) as ImageView
        icon.setImageDrawable(Utils.setDrawableSelector(this@MainActivity, mTabIconsSelected[position], mTabIconsSelected[position]))
        return view
    }


    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {

        super.onStop()
    }


    private fun switchTab(position: Int) {
        mNavController!!.switchTab(position)


        //        updateToolbarTitle(position);
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            android.R.id.home -> {


                onBackPressed()
                return true
            }
        }


        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {

        if (!mNavController!!.isRootFragment) {
            mNavController!!.popFragment()
        } else {

            if (fragmentHistory!!.isEmpty) {
                super.onBackPressed()
            } else {


                if (fragmentHistory!!.stackSize > 1) {

                    val position = fragmentHistory!!.popPrevious()

                    switchTab(position)

                    updateTabSelection(position)

                } else {

                    switchTab(0)

                    updateTabSelection(0)

                    fragmentHistory!!.emptyStack()
                }
            }

        }
    }


    private fun updateTabSelection(currentTab: Int) {

        for (i in TABS.indices) {
            val selectedTab = bottomTabLayout!!.getTabAt(i)
            if (currentTab != i) {
                selectedTab!!.customView!!.isSelected = false
            } else {
                selectedTab!!.customView!!.isSelected = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mNavController != null) {
            mNavController!!.onSaveInstanceState(outState)
        }
    }

    override fun pushFragment(fragment: Fragment) {
        if (mNavController != null) {
            mNavController!!.pushFragment(fragment)
        }
    }


    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        if (supportActionBar != null && mNavController != null) {


            updateToolbar()

        }
    }

    private fun updateToolbar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(!mNavController!!.isRootFragment)
        supportActionBar!!.setDisplayShowHomeEnabled(!mNavController!!.isRootFragment)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }


    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (supportActionBar != null && mNavController != null) {

            updateToolbar()

        }
    }

    override fun getRootFragment(index: Int): Fragment {
        when (index) {

            FragNavController.TAB1 -> return HomeFragment()
            FragNavController.TAB2 -> return SearchFragment()
            FragNavController.TAB3 -> return ShareFragment()
            FragNavController.TAB4 -> return NewsFragment()
            FragNavController.TAB5 -> return ProfileFragment()
        }
        throw IllegalStateException("Need to send an index that we know")
    }


    //    private void updateToolbarTitle(int position){
    //
    //
    //        getSupportActionBar().setTitle(TABS[position]);
    //
    //    }


    fun updateToolbarTitle(title: String) {


        supportActionBar!!.setTitle(title)

    }
}