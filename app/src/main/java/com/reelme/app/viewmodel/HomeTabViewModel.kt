package com.reelme.app.viewmodel


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.PagerAdapter
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.adapter.ViewPagerAdapter
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Validator
import com.reelme.app.view.FragmentEditProfile
import com.reelme.app.view.FragmentHomeTab


class HomeTabViewModel(private val fragmentSignin: FragmentHomeTab) : BaseObservable() {

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
    private val name = MutableLiveData<String>()

    init {
        getUserInfo()
    }


    fun signInUserClicked() {
        // fragmentSignin.finish()
        fragmentSignin.startActivity(Intent(fragmentSignin.activity, FragmentEditProfile::class.java));
    }

    fun getName(): LiveData<String?> {
        return name
    }

    lateinit var userDetails : UserModel
    private var isEdit = false;
    var percentage =""

    private fun getUserInfo() {
        val sharedPreference = fragmentSignin.activity!!.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)

            name!!.postValue(userDetails.firstName);


            percentage = Validator().profileRate(userDetails).toString()
            percentof = " $percentage"
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    @get:Bindable
    var photo: String? = userDetails.profilePic
        set(price) {
            field = price
            notifyPropertyChanged(BR.photo)
        }

    @get:Bindable
    var percentof: String? = " "+ Validator().profileRate(userDetails).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.percentof)
        }

    @get:Bindable
    var firstName: String? = userDetails.firstName
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }


    @get:Bindable
    var bio: String? = userDetails.bio
        set(price) {
            field = price
            notifyPropertyChanged(BR.bio)
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


     fun setUserInfo(){
        var userModel = UserModel()

        val gsonValue = Gson().toJson(userModel)
        val sharedPreference =  fragmentSignin.context!!.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()


    }

}