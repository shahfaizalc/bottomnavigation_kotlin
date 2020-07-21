package com.guiado.akbhar.viewmodel


import android.content.Intent
import android.util.Log
import android.view.View
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
import com.guiado.akbhar.view.FragmentNewDiscusssion
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.guiado.akbhar.R
import com.guiado.akbhar.model.Corona
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.model.SearchMode
import com.guiado.akbhar.view.FragmentCorona
import com.guiado.akbhar.view.WebViewActivity


class CoronaModel (internal var activity: FragmentActivity,
                   internal val fragmentProfileInfo: FragmentCorona) // To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Feed>
    var talentProfilesListCorona: ObservableArrayList<Corona>

    var query : Query
    var queryCorona : Query
    var db :FirebaseFirestore
    private val mAuth: FirebaseAuth

    var resetScrrollListener : Boolean = false;

    companion object {
        private val TAG = "DiscussionModel"
    }

    init {
        fragmentProfileInfo.mFragmentNavigation.viewToolbar(true);
        talentProfilesList = ObservableArrayList()
        talentProfilesListCorona = ObservableArrayList()

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)

        }
        query = db.collection("/NEWS/news_arabic/world").orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(10)
        queryCorona = db.collection("/NEWS/news_arabic/corona/")
        doGetCoronaUpdate()
        doGetTalents()
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


    @get:Bindable
    var online: Boolean = true
        set(city) {
            field = city
            notifyPropertyChanged(BR.online)
        }


    var profile = Profile();

    @Override
    fun doFindGroups2() = View.OnClickListener() {

        if(flag){
            flag = false;
            online = flag
            deathCount = talentProfilesListCorona[1].death
            recoveredCount = talentProfilesListCorona[1].recovered
            confirmedCount = talentProfilesListCorona[1].confirmed
        }
    }

    @Override
    fun doFindGroups3() = View.OnClickListener() {

        if(!flag){
            flag = true;
            online = flag
            deathCount = talentProfilesListCorona[0].death
            recoveredCount = talentProfilesListCorona[0].recovered
            confirmedCount = talentProfilesListCorona[0].confirmed
        }
    }

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
            val intent = Intent(activity, FragmentNewDiscusssion::class.java)
            activity.startActivity(intent)
        }
    }

    fun doGetCoronaUpdateItems(document: QueryDocumentSnapshot, id: String) {
        val adModel = document.toObject(Corona::class.java)
        Log.d(TAG, "Success doGetCoronaUpdateItems documents: " + adModel.confirmed)
        talentProfilesListCorona.add(adModel)
        if(document.id == "moroccocount") {
            setCoronaDefalultValue(adModel)
        }
    }

    var flag = true;
    private fun setCoronaDefalultValue(talentProfilesListCorona: Corona) {
            deathCount = talentProfilesListCorona.death
            recoveredCount = talentProfilesListCorona.recovered
            confirmedCount = talentProfilesListCorona.confirmed

    }

    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Feed::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.imgurl)

             if(!isUpdated) {
                 talentProfilesList.add(adModel)
             }
    }


    fun doGetCoronaUpdate() {

        Log.d(TAG, "DOIT doGetTalents: searchMode: "+ searchMode)

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

            Log.w(TAG, "Listen querySnapshot end"+querySnapshot.size())


            for (change in querySnapshot.documentChanges) {

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else{
                    "server"
                }
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city new: ")
                    doGetCoronaUpdateItems(change.document, change.document.id)

                }

                if (change.type == DocumentChange.Type.MODIFIED) {
                    Log.d(TAG, "New city modified: ")
                }
                Log.d(TAG, "Data fetched from $source")
            }
        }
    }




    var isUpdated = false

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
