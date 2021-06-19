package com.reelme.app.viewmodel


import android.content.Context
import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.Events.MyCustomEvent
import com.reelme.app.model2.Profile
import com.reelme.app.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.reelme.app.R
import com.reelme.app.model.SearchMode
import com.reelme.app.model_sales.Authenticaiton
import com.reelme.app.view.*
import kotlinx.coroutines.*


class ChallengeModel(internal var activity: FragmentActivity,
                     internal val fragmentProfileInfo: FragmentChallenges) // To show list of user images (Gallery)
    : BaseObservable() {


    var resetScrrollListener: Boolean = false;

    companion object {
        private val TAG = "DiscussionModel"
    }


    init {
        fragmentProfileInfo.mFragmentNavigation.viewToolbar(true);
        doGetTalents()
    }


    @get:Bindable
    var finderTitle: String? = activity.resources.getString(R.string.finderEventTitle)
        set(city) {
            field = city
            notifyPropertyChanged(BR.finderTitle)
        }


    @get:Bindable
    var showClearFilter: Int = View.GONE
        set(city) {
            field = city
            notifyPropertyChanged(BR.showClearFilter)
        }

    @get:Bindable
    var searchMode = SearchMode.DEFAULT
        set(city) {
            field = city

            if (searchMode.ordinal == SearchMode.DEFAULT.ordinal)
                showClearFilter = View.GONE
            else {
                showClearFilter = View.VISIBLE

            }
        }

    /*
      Method will act as the event handler for MyCustomEvent.kt
      */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
    }

    var profile = Profile();




    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
       val intent = Intent(activity, FragmentNewChallenge::class.java)
            activity.startActivity(intent)
        }
    }



    @Override
    fun onFilterClearClick() = View.OnClickListener() {
        showClearFilter = View.GONE
        searchMode = SearchMode.DEFAULT
        resetScrrollListener = true

    }

    @Override
    fun onFilterClick() = View.OnClickListener() {

        if (!handleMultipleClicks()) {


                filterByCategory(0)

        }
    }



    fun filterByCategory(position: Int) {
        doGetTalents()
    }

    fun getAccessToken(): String {
        val sharedPreference = activity.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");
        try {
            val auth = Gson().fromJson(coronaJson, Authenticaiton::class.java)
            return auth.accessToken

        } catch (e: java.lang.Exception) {
            return ""
        }
    }



    fun doGetTalents() {

    }



}


//
