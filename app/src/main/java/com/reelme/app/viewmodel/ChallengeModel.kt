package com.reelme.app.viewmodel


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.Events.MyCustomEvent
import com.reelme.app.GetServiceNews
import com.reelme.app.model2.Profile
import com.reelme.app.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.reelme.app.R
import com.reelme.app.model.SearchMode
import com.reelme.app.model_sales.Authenticaiton
import com.reelme.app.model_sales.challenges.QueryChallenges
import com.reelme.app.view.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChallengeModel(internal var activity: FragmentActivity,
                     internal val fragmentProfileInfo: FragmentChallenges) // To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<com.reelme.app.model_sales.challenges.Record>

    var resetScrrollListener: Boolean = false;

    companion object {
        private val TAG = "DiscussionModel"
    }


    init {
        fragmentProfileInfo.mFragmentNavigation.viewToolbar(true);
        talentProfilesList = ObservableArrayList()
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
        talentProfilesList.removeAll(talentProfilesList)

    }

    @Override
    fun onFilterClick() = View.OnClickListener() {

        if (!handleMultipleClicks()) {


                filterByCategory(0)

        }
    }



    fun filterByCategory(position: Int) {
        talentProfilesList.removeAll(talentProfilesList)

        doGetTalents()
    }

    lateinit var postsService: GetServiceNews


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

//        val intent = Intent(activity, FragmentReelType1::class.java)
//        activity.startActivity(intent)

//        Log.d(TAG, "DOIT doGetTalents: searchMode: " + searchMode)
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl("https://philipscrm--pocinc.my.salesforce.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .build()
//        postsService = retrofit.create(GetServiceNews::class.java)
//        sendPost(getAccessToken())


    }

    private fun sendPost(accesstoken: String) {

        //  showProgresss(true)
        Log.d("Authenticaiton2 token", "send post");

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                val repositories = withContext(Dispatchers.Default) {
                    postsService.getQueryChallenges("services/data/v49.0/query/?q=SELECT+id,name+from+ICP_Challenge__c", "Bearer "+accesstoken).await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories) }
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d("TAG", "coroutineHandler:exception ${exception}")

    }

    private fun coroutineSuccessHandler(response: QueryChallenges) {
        Log.d("TAG", "coroutineHandler:Success ${response}")

        var queries = response.records.size
        Log.d("TAG", "coroutineHandler:Success ${queries}")
        talentProfilesList.addAll(response.records)


    }


}


//
