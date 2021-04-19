package com.reelme.app.view

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.reelme.app.R
import com.reelme.app.adapter.ViewPagerAdapter
import com.reelme.app.databinding.ActivityFriendsBinding
import com.reelme.app.databinding.ActivityHometabBinding
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.util.Constants
import com.reelme.app.viewmodel.FiendsViewModel
import com.reelme.app.viewmodel.HomeTabViewModel


class FragmentFriends : BaseFragment() {

    internal var binding: ActivityFriendsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = ActivityFriendsBinding.inflate(inflater, container, false)
            val areaViewModel = FiendsViewModel(this)
            binding!!.homeData = areaViewModel
            binding!!.homeData!!.setPagerAdapter(adapter)

            binding!!.homeData!!.getName().observe(this) { name -> setTitle(name) }
        }

        return binding!!.root
    }

    private fun setTitle(name: String?) {
        this.mFragmentNavigation.updateToolbarTitle(name!!+"\'s Profile")
    }

    val adapter: ViewPagerAdapter
        get() {
            val adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
            val number = resources.getStringArray(R.array.tabs_follow)
            for (i in number.indices) {
                adapter.addFrag(Constants.FOLLOW_FRAGMENTS[i] as Fragment, number[i])
            }
            return adapter
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_example, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_ic -> {
                Toast.makeText(context, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.settings_ic ->{
                val intent = Intent(activity, FragmentSettingsList::class.java);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                activity!!.startActivity(intent);
                return true
            }
            R.id.refer_ic ->{
                return true
            }
            R.id.influnce_ic ->{
                return true
            }

            R.id.logout ->{
                logout()
                Toast.makeText(context, "Logout", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun logout() {
            FirebaseAuth.getInstance().signOut();
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
            binding!!.homeData!!.setUserInfo();
            activity!!.finish();

            val intent = Intent(activity, FragmentWelcome::class.java);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity!!.startActivity(intent);

    }

}
