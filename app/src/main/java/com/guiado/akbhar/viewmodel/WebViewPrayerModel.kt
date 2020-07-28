package com.guiado.akbhar.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.gson.Gson
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.listeners.WebViewCallback
import com.guiado.akbhar.model.Corona
import com.guiado.akbhar.model.Covid19
import com.guiado.akbhar.util.firestoreSettings
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.utils.MyWebViewClient
import com.guiado.akbhar.view.*


/**
 * The view model class to show the blog article in a web view
 *
 */
class WebViewPrayerModel(internal val activity: FragmentActivity) : BaseObservable(), WebViewCallback {

    private final val TAG = "DiscussionModel"

    var db: FirebaseFirestore
    var talentProfilesListCorona: ObservableArrayList<Corona>

    var flag = true;

    init {
        talentProfilesListCorona = ObservableArrayList()

        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e: Exception) {
            Log.d(TAG, "getProfile  " + e)

        }

        doGetCoronaUpdate()

    }

    fun doGetCoronaUpdate() {
        val sharedPreference = activity.getSharedPreferences("COVID_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("COVID_INFO", "");

        try {
            val result = Gson().fromJson(coronaJson, Array<Corona>::class.java)
            talentProfilesListCorona.add(result.get(0))
            talentProfilesListCorona.add(result.get(1))
        } catch (e: java.lang.Exception) {

        }
    }

    /**
     * Web view  url
     */
    var webViewUrl = ""

    /**
     * To show progress : webview onload progress
     */
    @get:Bindable
    var webViewVisible = View.GONE
        set(webViewVisible) {
            field = webViewVisible
            notifyPropertyChanged(BR.webViewVisible)
        }

    /**
     * To show progress : webview onload progress
     */
    @get:Bindable
    var progressBarVisible = View.GONE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    /**
     * User Notification  visibility
     */
    @get:Bindable
    var msgView = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msgView)
        }

    /**
     * User Notification  text
     */
    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    /**
     * User Notification  text
     */
    @get:Bindable
    var prayershowhide: String? = activity.resources.getString(R.string.prayer_time_click_show)
        set(prayershowhide) {
            field = prayershowhide
            notifyPropertyChanged(BR.prayershowhide)
        }

    /**
     * To avail WebViewClient
     */
    @Bindable
    fun getWebViewClient(): WebViewClient {
        progressBarVisible = View.VISIBLE
        return MyWebViewClient(this)
    }

    /**
     * On  webview onLoad success
     */
    override fun onSuccess() {
        Log.d(TAG, "onSuccess: Webpage loading successful")
        progressBarVisible = View.GONE
    }

    /**
     * On webview onLoad failed
     * @param err : Error  message
     */
    override fun onError(err: String) {
        Log.d(TAG, "onError: Webpage loading failed: Error " + err)
        progressBarVisible = View.GONE
    }

    var showOrHidePrayerTable = false

    @Override
    fun showprayertable() = View.OnClickListener() {
        if (showOrHidePrayerTable) {
            webViewVisible = View.GONE
            showOrHidePrayerTable = false
            prayershowhide = activity.resources.getString(R.string.prayer_time_click_show)
        } else {
            showOrHidePrayerTable = true
            webViewVisible = View.VISIBLE
            prayershowhide = activity.resources.getString(R.string.prayer_time_click_hide)
        }
    }


    @Override
    fun doFindGroups2() = View.OnClickListener() {

        if (flag) {
            flag = false;
            online = flag
            deathCount = talentProfilesListCorona[1].death
            recoveredCount = talentProfilesListCorona[1].recovered
            confirmedCount = talentProfilesListCorona[1].confirmed
        }
    }

    @Override
    fun doFindGroups3() = View.OnClickListener() {

        if (!flag) {
            flag = true;
            online = flag
            deathCount = talentProfilesListCorona[0].death
            recoveredCount = talentProfilesListCorona[0].recovered
            confirmedCount = talentProfilesListCorona[0].confirmed
        }
    }


    @Override
    fun doFindGroups4() = View.OnClickListener() {
        val intentNext = Intent(activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, "https://covid-19.ontario.ca/self-assessment/")
        activity.startActivity(intentNext)

    }

    @Override
    fun openNewsNArt() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentArt::class.java)
        activity.startActivity(intentNext)
    }

    @Override
    fun openNewsFood() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentFood::class.java)
        activity.startActivity(intentNext)
    }

    @Override
    fun openNewsTravel() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentTravel::class.java)
        activity.startActivity(intentNext)
    }

    @Override
    fun openNewsGame() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentGame::class.java)
        activity.startActivity(intentNext)
    }

    @Override
    fun openNewsScience() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentScience::class.java)
        activity.startActivity(intentNext)
    }

    @Override
    fun openNewsTech() = View.OnClickListener() {
        val intentNext = Intent(activity, FragmentTechnology::class.java)
        activity.startActivity(intentNext)
    }

    @get:Bindable
    var deathCount: String? = talentProfilesListCorona[0].death
        set(city) {
            field = city
            notifyPropertyChanged(BR.deathCount)
        }

    @get:Bindable
    var recoveredCount: String? = talentProfilesListCorona[0].recovered
        set(city) {
            field = city
            notifyPropertyChanged(BR.recoveredCount)
        }

    @get:Bindable
    var confirmedCount: String? = talentProfilesListCorona[0].confirmed
        set(city) {
            field = city
            notifyPropertyChanged(BR.confirmedCount)
        }

    @get:Bindable
    var online: Boolean = true
        set(city) {
            field = city
            notifyPropertyChanged(BR.online)
        }

}