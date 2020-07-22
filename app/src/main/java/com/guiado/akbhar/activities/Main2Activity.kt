package com.guiado.akbhar.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.guiado.akbhar.R
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.network.FirbaseReadHandler
import com.guiado.akbhar.util.getUserName
import com.guiado.akbhar.utils.FragmentHistory
import com.guiado.akbhar.utils.Utils
import com.guiado.akbhar.view.*
import com.guiado.akbhar.views.FragNavController


class Main2Activity : BaseActivity(), BaseFragment.FragmentNavigation,
        FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    internal var contentFrame: FrameLayout? = null

    internal var toolbar: Toolbar? = null

    private val mTabIconsSelected = intArrayOf(R.drawable.tab_home,
            R.drawable.tab_group, R.drawable.tab_book, R.drawable.tab_list)


    internal lateinit var TABS: ArrayList<String>

    internal var bottomTabLayout: TabLayout? = null

    private var mNavController: FragNavController? = null

    private var fragmentHistory: FragmentHistory? = null

    private lateinit var mAuth: FirebaseAuth


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
        //mAuth = FirebaseAuth.getInstance()
       // if(mAuth.currentUser != null && mAuth.currentUser!!.isEmailVerified) {

          //  updateName()
            switchTab(0)
            updateTabSelection(0)
//        }else{
//           // bottomTabLayout!!.visibility = View.GONE
//            switchTab(3)
//            updateTabSelection(3)
//        }
    }

    private fun updateName() {
        val ksk = getUserName(this.applicationContext, mAuth.currentUser?.uid!!);

        if (ksk.name == null) {
            FirbaseReadHandler().storeUserNamePreference(this.applicationContext)
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
            } else if(mAuth.currentUser==null){
                super.onBackPressed()

            }else {

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
        }    }

    override fun pushFragment(fragment: Fragment) {
        if (mNavController != null) {
            mNavController!!.pushFragment(fragment)
        }
    }

    override fun replaceFragment(fragment: Fragment) {
        if (mNavController != null) {
            mNavController!!.replaceFragment(fragment)
        }    }


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
            FragNavController.TAB2 -> return FragmentCorona()
            FragNavController.TAB3 -> return FragmentMagazine()
            FragNavController.TAB4 -> return FragmentIntro()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    fun updateToolbarTitle( position:Int){
        supportActionBar!!.title = TABS.get(position)

    }

    override fun viewBottom(viewState: Int) {
        bottomTabLayout!!.visibility = viewState;
    }

    override fun viewToolbar(b: Boolean) {
        if(b) {
            toolbar?.visibility = View.VISIBLE
        } else {
            toolbar?.visibility = View.GONE
        }
    }


    fun updateToolbarTitle(title: String) {


        supportActionBar!!.title= title

    }


}