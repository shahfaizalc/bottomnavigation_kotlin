package com.guiado.akbhar.viewmodel

import android.content.Intent
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.*
import com.guiado.akbhar.listeners.WebViewCallback
import com.guiado.akbhar.utils.MyWebViewClient
import com.guiado.akbhar.BR
import com.guiado.akbhar.model.Corona
import com.guiado.akbhar.util.firestoreSettings
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.*
import com.guiado.akbhar.R


/**
 * The view model class to show the blog article in a web view
 *
 */
class WebViewPrayerModel(internal val activity: FragmentActivity) : BaseObservable(), WebViewCallback {

    private final val TAG = "DiscussionModel"


    var queryCorona: Query
    var db: FirebaseFirestore
    var talentProfilesListCorona: ObservableArrayList<Corona>


    init {
        talentProfilesListCorona = ObservableArrayList()

        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e: Exception) {
            Log.d(TAG, "getProfile  " + e)

        }


        queryCorona = db.collection("/NEWS/news_arabic/corona/")
        doGetCoronaUpdate()
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
    var prayershowhide: String? = activity.resources.getString(R.string.prayer_time)
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
            prayershowhide =  activity.resources.getString(R.string.prayer_time)
        } else {
            showOrHidePrayerTable = true
            webViewVisible = View.VISIBLE
            prayershowhide = activity.resources.getString(R.string.prayer_time_hide)
        }
    }


    @Override
    fun doFindGroups2() = View.OnClickListener() {

        if (flag) {
            flag = false;
            online = flag
            deathCount = talentProfilesListCorona[0].death
            recoveredCount = talentProfilesListCorona[0].recovered
            confirmedCount = talentProfilesListCorona[0].confirmed
        }
    }

    @Override
    fun doFindGroups3() = View.OnClickListener() {

        if (!flag) {
            flag = true;
            online = flag
            deathCount = talentProfilesListCorona[1].death
            recoveredCount = talentProfilesListCorona[1].recovered
            confirmedCount = talentProfilesListCorona[1].confirmed
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


    fun doGetCoronaUpdateItems(document: QueryDocumentSnapshot, id: String) {
        val adModel = document.toObject(Corona::class.java)
        Log.d(TAG, "Success doGetCoronaUpdateItems documents: " + adModel.confirmed)
        talentProfilesListCorona.add(adModel)
        if (document.id == "moroccocount") {
            setCoronaDefalultValue(adModel)
        }
    }

    @get:Bindable
    var deathCount: String? = ""
        set(city) {
            field = city
            notifyPropertyChanged(BR.deathCount)
        }

    @get:Bindable
    var recoveredCount: String? = ""
        set(city) {
            field = city
            notifyPropertyChanged(BR.recoveredCount)
        }

    @get:Bindable
    var confirmedCount: String? = ""
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


    var flag = true;
    private fun setCoronaDefalultValue(talentProfilesListCorona: Corona) {
        deathCount = talentProfilesListCorona.death
        recoveredCount = talentProfilesListCorona.recovered
        confirmedCount = talentProfilesListCorona.confirmed
        flag = false;
        online = flag
    }


    fun doGetCoronaUpdate() {

        Log.d(TAG, "DOIT doGetTalents: searchMode: ")

        // talentProfilesList.clear()
        queryCorona.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen error", e)
                return@addSnapshotListener
            }

            if (querySnapshot == null) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            if (querySnapshot.size() < 1) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            Log.w(TAG, "Listen querySnapshot end" + querySnapshot.size())


            for (change in querySnapshot.documentChanges) {

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else {
                    "server"
                }
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city new: ")
                    doGetCoronaUpdateItems(change.document, change.document.id)
                }

                if (change.type == DocumentChange.Type.MODIFIED) {
                    // doGetCoronaUpdateItems(change.document, change.document.id)
                    Log.d(TAG, "New city modified: ")
                }
                Log.d(TAG, "Data fetched from $source")
            }
        }
    }


}