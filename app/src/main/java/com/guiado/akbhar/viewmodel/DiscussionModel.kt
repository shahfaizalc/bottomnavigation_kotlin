package com.guiado.akbhar.viewmodel


import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.guiado.akbhar.BR
import com.guiado.akbhar.Events.MyCustomEvent
import com.guiado.akbhar.model2.Bookmarks
import com.guiado.akbhar.model2.PostDiscussion
import com.guiado.akbhar.model2.Profile
import com.guiado.akbhar.util.*
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.FragmentDiscussions
import com.guiado.akbhar.view.FragmentNewDiscusssion
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.guiado.akbhar.R
import com.guiado.akbhar.adapter.CustomAdapter
import com.guiado.akbhar.model.CoachItem
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.model.SearchMode
import com.guiado.akbhar.view.WebViewActivity
import java.util.ArrayList


class DiscussionModel (internal var activity: FragmentActivity,
                      internal val fragmentProfileInfo: FragmentDiscussions) // To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Feed>
    var query : Query
    var db :FirebaseFirestore
    private val mAuth: FirebaseAuth

    var resetScrrollListener : Boolean = false;

    companion object {
        private val TAG = "DiscussionModel"
    }

    init {
        fragmentProfileInfo.mFragmentNavigation.viewToolbar(true);
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)

        }
        query = db.collection("/NEWS/news_arabic/world").orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(10)
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

            if(searchMode.ordinal == SearchMode.DEFAULT.ordinal)
                showClearFilter = View.GONE
            else{
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


    fun openFragment2(postAdModel: Feed, position: Int) {
        val intentNext = Intent(activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.newsurl)
        activity.startActivity(intentNext)
    }

    fun openFragment3(postAdModel: Feed, position: Int) {
        val intentNext = Intent(activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.homeurl)
        activity.startActivity(intentNext)
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if(!handleMultipleClicks()) {
//            val fragment = FragmentNewDiscusssion()
//            val bundle = Bundle()
//            fragment.setArguments(bundle)
//            fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));
//
            val intent = Intent(activity, FragmentNewDiscusssion::class.java)
            activity.startActivity(intent)
        }
    }

    private fun readAutoFillItems() : ArrayList<CoachItem> {
        val values = GenericValues()
        return values.readDisuccsionTopics(activity.applicationContext)
    }


    @Override
    fun onFilterClearClick() = View.OnClickListener() {
        showClearFilter = View.GONE
        searchMode = SearchMode.DEFAULT
        query = db.collection("discussion").orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
        resetScrrollListener = true
        talentProfilesList.removeAll(talentProfilesList)

        doGetTalents()
    }

    @Override
    fun onFilterClick() = View.OnClickListener() {

        if(!handleMultipleClicks()) {

            val dialog = Dialog(activity)
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_listview)

            val btndialog: TextView = dialog.findViewById(R.id.btndialog) as TextView
            btndialog.setOnClickListener({ dialog.dismiss() })

            val items = readAutoFillItems()
            val listView: ListView = dialog.findViewById(R.id.listview) as ListView
            val customAdapter = CustomAdapter(readAutoFillItems(), activity.applicationContext)
            listView.setAdapter(customAdapter)

            listView.setOnItemClickListener({ parent, view, position, id ->

                dialog.dismiss()

                filterByCategory(position)
            })

            dialog.show()
        }
    }

    private fun getCommbinationWords(s: String): List<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1
    }

    fun doGetTalentsSearch(searchQuery: String) {
            query = db.collection("discussion")
                    .whereArrayContainsAny("searchTags", getCommbinationWords(searchQuery).toList())
                    .orderBy("postedDate", Query.Direction.DESCENDING)
                    .limit(10)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Feed::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.imgurl)

       //  if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) && (adModel.eventState.ordinal == EventStatus.SHOWING.ordinal)) {


           //  getKeyWords(talentProfilesList,adModel)

             if(!isUpdated) {
                 talentProfilesList.add(adModel)
             }
        // }
    }

    var isUpdated = false

//    private fun getKeyWords(keyWords: ObservableArrayList<Feed>,keyWord: PostDiscussion): ObservableArrayList<PostDiscussion> {
//        isUpdated = false
//
//        var count = 0;
//
//        keyWords.notNull {
//            val numbersIterator = it.iterator()
//            numbersIterator.let {
//                while (numbersIterator.hasNext()) {
//                    val value = (numbersIterator.next())
//                    if (value.postedDate.equals(keyWord.postedDate)){
//                        Log.d(TAG, "Success getting fai documents: set " )
//                        isUpdated = true
//                        talentProfilesList.set(count,keyWord)
//                        return@notNull
//                    }
//                    count = count + 1;
//                }
//            }
//        }
//        return keyWords;
//    }

    fun filterByCategory(position: Int) {
        query = db.collection("discussion").orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
                .whereArrayContains("keyWords",position)
        talentProfilesList.removeAll(talentProfilesList)

        if(searchMode.ordinal == SearchMode.DEFAULT.ordinal)
            searchMode = SearchMode.CATEGORY
        else{
            searchMode = SearchMode.CATEGORYANDSEARCH

        }
        doGetTalents()
    }

    fun doGetTalents() {

        Log.d(TAG, "DOIT doGetTalents: searchMode: "+ searchMode)

       // talentProfilesList.clear()
        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
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

            Log.w(TAG, "Listen querySnapshot end"+querySnapshot.size())

            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]

            query = query.startAfter(lastVisible)


            for (change in querySnapshot.documentChanges) {

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else{
                    "server"
                }
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city new: ")
                    addTalentsItems(change.document)
                }

                if (change.type == DocumentChange.Type.MODIFIED) {
                    Log.d(TAG, "New city modified: ")
                }
                Log.d(TAG, "Data fetched from $source")
            }
        }
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
