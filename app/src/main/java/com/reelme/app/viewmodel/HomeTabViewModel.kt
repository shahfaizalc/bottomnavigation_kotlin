package com.reelme.app.viewmodel


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.viewpager.widget.PagerAdapter
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.adapter.ViewPagerAdapter
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.FragmentDate
import com.reelme.app.view.FragmentHomeTab
import com.reelme.app.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class HomeTabViewModel(private val  fragmentSignin: FragmentHomeTab) : BaseObservable() {

    /**
     * TAG
     */
    private val TAG = "HomeViewModel"

    /**
     * View Pager Adapter
     */
    private var adapter: ViewPagerAdapter? = null

    val pagerAdapter: PagerAdapter?
        @Bindable
        get() = adapter

    fun setPagerAdapter(adapter: ViewPagerAdapter) {
        Log.d(TAG, "setPagerAdapter")
        this.adapter = adapter
        notifyPropertyChanged(BR.pagerAdapter)
    }

    init {
        getUserInfo()
    }


    fun signInUserClicked() {
        // fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin.activity, FragmentEditProfile::class.java));
    }

    lateinit var userDetails : UserModel
    private var isEdit = false;

    private fun getUserInfo() {
        val sharedPreference = fragmentSignin.activity!!.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT",false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }



    @get:Bindable
    var firstName: String? = userDetails.firstName
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }

    private fun showToast(id: Int) {
        Toast.makeText(fragmentSignin.context, fragmentSignin.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }
}
