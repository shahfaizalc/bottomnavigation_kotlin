package com.reelme.app.viewmodel


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.Events.MyCustomEvent
import com.reelme.app.GetServiceNews
import com.reelme.app.R
import com.reelme.app.model.SearchMode
import com.reelme.app.model2.Bookmarks
import com.reelme.app.model2.PostDiscussion
import com.reelme.app.model2.Profile
import com.reelme.app.model_sales.Authenticaiton
import com.reelme.app.model_sales.ideas.QueryIdeas
import com.reelme.app.model_sales.ideas.Record
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.util.notNull
import com.reelme.app.view.FragmentDiscussions
import com.reelme.app.view.FragmentNewDiscusssion
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DiscussionModel(internal var activity: FragmentActivity,
                      internal val fragmentProfileInfo: FragmentDiscussions) // To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Record>

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
    val REQUEST_CODE = 11

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
//            val fragment = FragmentNewDiscusssion()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));
//
            val intent = Intent(activity, FragmentNewDiscusssion::class.java)
            activity.startActivityForResult(intent,REQUEST_CODE)
            Log.d("on result faizal","on result faizaltrp")

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

//            val dialog = Dialog(activity)
//            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.dialog_listview)
//
//            val btndialog: TextView = dialog.findViewById(R.id.btndialog) as TextView
//            btndialog.setOnClickListener({ dialog.dismiss() })
//
//            val items = readAutoFillItems()
//            val listView: ListView = dialog.findViewById(R.id.listview) as ListView
//            val customAdapter = CustomAdapter(readAutoFillItems(), activity.applicationContext)
//            listView.setAdapter(customAdapter)
//
//            listView.setOnItemClickListener { parent, view, position, id ->
//
//                dialog.dismiss()

                filterByCategory(0)
//            }
//
//            dialog.show()
        }
    }


//
//    fun addTalentsItems(document: QueryDocumentSnapshot) {
//
//        val adModel = document.toObject(PostDiscussion::class.java)
//
//        Log.d(TAG, "Success getting documents: " + adModel.postedBy)
//
//        //    if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) && (adModel.eventState.ordinal == EventStatus.SHOWING.ordinal)) {
//
//        getKeyWords(talentProfilesList, adModel)
//
//        if (!isUpdated) {
//            talentProfilesList.add(adModel)
//        }
//        //  }
//    }

    var isUpdated = false

//    private fun getKeyWords(keyWords: ObservableArrayList<PostDiscussion>, keyWord: PostDiscussion): ObservableArrayList<PostDiscussion> {
//        isUpdated = false
//
//        var count = 0;
//
//        keyWords.notNull {
//            val numbersIterator = it.iterator()
//            numbersIterator.let {
//                while (numbersIterator.hasNext()) {
//                    val value = (numbersIterator.next())
//                    if (value.postedDate.equals(keyWord.postedDate)) {
//                        Log.d(TAG, "Success getting fai documents: set ")
//                        isUpdated = true
//                        talentProfilesList.set(count, keyWord)
//                        return@notNull
//                    }
//                    count = count + 1;
//                }
//            }
//        }
//        return keyWords;
//    }

    fun filterByCategory(position: Int) {
        talentProfilesList.removeAll(talentProfilesList)

//        if (searchMode.ordinal == SearchMode.DEFAULT.ordinal)
//            searchMode = SearchMode.CATEGORY
//        else {
//            searchMode = SearchMode.CATEGORYANDSEARCH
//
//        }
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

    fun getUserId(): String {
        val sharedPreference = activity.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");
        try {
            val auth = Gson().fromJson(coronaJson, Authenticaiton::class.java)
            return auth.signature

        } catch (e: java.lang.Exception) {
            return ""
        }
    }


    fun doGetTalents() {

        Log.d(TAG, "DOIT doGetTalents: searchMode: " + searchMode)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://philipscrm--pocinc.my.salesforce.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        postsService = retrofit.create(GetServiceNews::class.java)
        sendPost(getAccessToken())


    }

    private fun sendPost(accesstoken: String) {

        //  showProgresss(true)
        Log.d("Authenticaiton2 token", "send post");

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                val repositories = withContext(Dispatchers.Default) {
                    postsService.getQueryIdeas("services/data/v49.0/query/?q=SELECT+name,Benefit__c,Description__c,Status__c,Impact__c+from+ICP_Idea__c", "Bearer "+accesstoken).await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories) }
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d("TAG", "coroutineHandler:exception ${exception}")

    }

    private fun coroutineSuccessHandler(response: QueryIdeas) {
        Log.d("TAG", "coroutineHandler:Success ${response}")

        var queries = response.records.size
        Log.d("TAG", "coroutineHandler:Success ${queries}")
        talentProfilesList.addAll(response.records)


    }


    fun isBookmarked(postDiscussion: PostDiscussion): Boolean? {
        var isFollow = false
        postDiscussion.bookmarks.notNull {
            val likes: MutableIterator<Bookmarks> = it.iterator()
            while (likes.hasNext()) {
                val name = likes.next()
                if (name.markedById.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
                    isFollow = true
                }
            }
        }

        return isFollow
    }
}


//