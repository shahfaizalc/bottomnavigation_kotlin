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
import com.guiado.akbhar.util.*
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.FragmentDiscussions
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.view.FragmentTechnology
import com.guiado.akbhar.view.WebViewActivity


class TechViewModel (internal var activity: FragmentActivity,
                     internal val fragmentProfileInfo: FragmentTechnology) // To show list of user images (Gallery)
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
        query = db.collection("/NEWS/news_arabic/world").whereEqualTo("newstype", 2).orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(10)
        doGetTalents()
    }




    @get:Bindable
    var showClearFilter: Int = View.GONE
        set(city) {
            field = city
            notifyPropertyChanged(BR.showClearFilter)
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

    fun doGetTalents() {

        Log.d(TAG, "DOIT doGetTalents:")

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

}


//
