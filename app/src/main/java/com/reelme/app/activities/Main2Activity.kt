package com.reelme.app.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.model_sales.Authenticaiton
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FragmentHistory
import com.reelme.app.utils.Utils
import com.reelme.app.view.*
import com.reelme.app.views.FragNavController


class Main2Activity : BaseActivity(), BaseFragment.FragmentNavigation,
        FragNavController.TransactionListener, FragNavController.RootFragmentListener {


    internal var contentFrame: FrameLayout? = null

    internal var toolbar: Toolbar? = null

    private val mTabIconsSelected = intArrayOf(R.drawable.ic_home,
            R.drawable.ic_user_whilte, R.drawable.ic_reel, R.drawable.ic_links,R.drawable.ic_search)


    internal lateinit var TABS: ArrayList<String>

    internal var bottomTabLayout: TabLayout? = null

    private var mNavController: FragNavController? = null

    private var fragmentHistory: FragmentHistory? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomTabLayout = findViewById(R.id.bottom_tab_layout)
        TABS = ArrayList();
        TABS.apply {
            add("Home")
            add("Share")
            add("News")
            add("Profile")
            add("Profile2")

        }


        toolbar = findViewById(R.id.toolbar)
        toolbar?.visibility = View.GONE

        initToolbar()

        initTab()

        fragmentHistory = FragmentHistory()


        mNavController = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.size)
                .build()


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

        showTab()


    }

    private fun showTab() {

        if (doGetCoronaUpdate()) {
            switchTab(2)
            updateTabSelection(2)
        } else {
            bottomTabLayout!!.visibility = View.GONE
            switchTab(2)
            updateTabSelection(2)
        }
    }

    fun doGetCoronaUpdate(): Boolean {

        val sharedPreference = getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        try {
            Gson().fromJson(coronaJson, UserModel::class.java)
            return true;
        } catch (e: java.lang.Exception) {
            return false
        }
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
        val view = LayoutInflater.from(this@Main2Activity).inflate(R.layout.tab_item_bottom, null)
        val icon = view.findViewById(R.id.tab_icon) as ImageView
        icon.setImageDrawable(Utils.setDrawableSelector(this@Main2Activity, mTabIconsSelected[position], mTabIconsSelected[position]))
        return view
    }


    override fun switchTab(position: Int) {
        mNavController!!.switchTab(position)
        updateTabSelection(position);
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
            } else if (doGetCoronaUpdate()) {
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
            selectedTab!!.customView!!.isSelected = currentTab == i
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mNavController != null) {
            mNavController!!.onSaveInstanceState(outState)
        }
    }

    override fun popFragment(fragment: Int) {
        if (mNavController != null) {
            mNavController!!.popFragments(fragment)
        }
    }

    override fun pushFragment(fragment: Fragment) {
        if (mNavController != null) {
            mNavController!!.pushFragment(fragment)
        }
    }

    override fun replaceFragment(fragment: Fragment) {
        if (mNavController != null) {
            mNavController!!.replaceFragment(fragment)
        }
    }


    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        viewToolbar(true)
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

            FragNavController.TAB1 -> return FragmentChallenges()
            FragNavController.TAB2 -> return FragmentDiscussions()
            FragNavController.TAB3 -> return FragmentHomeTab()
            FragNavController.TAB4 -> return FragmentChallenges()
            FragNavController.TAB5 -> return FragmentProfile()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    fun updateToolbarTitle(position: Int) {
        supportActionBar!!.title = TABS.get(position)

    }

    override fun viewBottom(viewState: Int) {
        bottomTabLayout!!.visibility = viewState;
    }

    override fun viewToolbar(b: Boolean) {
        if (b) {
            toolbar?.visibility = View.VISIBLE
        } else {
            toolbar?.visibility = View.GONE
        }
    }


    override fun updateToolbarTitle(title: String) {


        supportActionBar!!.title = title

    }


}