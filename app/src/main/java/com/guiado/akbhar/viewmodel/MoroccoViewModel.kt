package com.guiado.akbhar.viewmodel


import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.*
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.communication.RecyclerLoadCovidHandler
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.model.LanguageRegionEnum
import com.guiado.akbhar.model.ReadAssetFile
import com.guiado.akbhar.model.RegionEnum
import com.guiado.akbhar.util.*
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.utils.Constants.LANGUAGE_ID
import com.guiado.akbhar.utils.SharedPreference
import com.guiado.akbhar.view.FragmentMorocco
import com.guiado.akbhar.view.WebViewActivity
import java.util.*


class MoroccoViewModel(internal var activity: FragmentActivity,
                       internal val fragmentProfileInfo: FragmentMorocco) // To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Feed>
    var query: Query

    var talentHeadlineList: ObservableArrayList<Feed>
    var queryHeadline: Query

    var db: FirebaseFirestore

    var resetScrrollListener: Boolean = false;

    companion object {
        private val TAG = "DiscussionModel"
    }

    init {
        fragmentProfileInfo.mFragmentNavigation.viewToolbar(true);
        talentProfilesList = ObservableArrayList()
        talentHeadlineList = ObservableArrayList()

        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e: Exception) {
            Log.d(TAG, "getProfile  " + e)
        }
        var pref = SharedPreference(activity.applicationContext).getValueString(LANGUAGE_ID)
        Log.d(TAG, "getProfile  " + pref)

        if (pref!!.isEmpty()) {
            pref = LanguageRegionEnum.FR.name
        }

        query = db.collection("/NEWS/news_arabic/world").whereEqualTo(LANGUAGE_ID, pref).whereEqualTo("regionid", RegionEnum.MA.name).orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(20)
        queryHeadline = db.collection("/NEWS/news_arabic/world").whereEqualTo(LANGUAGE_ID, pref).orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(5)

        doGetTalents()
        doGetTalentsHeadline()
        setTime()
        setQuote()

        // Recycler view load more items
        val recyclerLoadCovidHandler = RecyclerLoadCovidHandler()

        recyclerLoadCovidHandler.initRequest(activity.applicationContext!!)

    }

    fun setQuote(): String? {

        val assetManager = activity.applicationContext.assets

        val readAssetFile = ReadAssetFile()
        val fileString = readAssetFile.readFile(assetManager, "quotes.json")

        val jsonToClassObject = JsonToClassObject(activity)
        var model = jsonToClassObject.fetchChaptersInfoJsonData(fileString)

        var listOFQuotes = model.get(0).quotes

        val rand = Random() //instance of random class
        //generate random values from 0-24
        //generate random values from 0-24
        val int_random = rand.nextInt(41)

        return listOFQuotes[int_random].arabic

    }

    /**
     * User Notification  text
     */
    @get:Bindable
    var dailyQuote: String? = setQuote()
        set(dailyQuote) {
            field = dailyQuote
            notifyPropertyChanged(BR.dailyQuote)
        }

    private fun setTime(): String {
        val rightNow: Calendar = Calendar.getInstance()
        val currentHourIn24Format: Int = rightNow.get(Calendar.HOUR_OF_DAY)

        if (currentHourIn24Format < 13) {
            return activity.resources.getString(R.string.goodmoring)
        } else {
            return activity.resources.getString(R.string.goodevening)
        }
    }


    /**
     * User Notification  text
     */
    @get:Bindable
    var prayershowhide: String? = setTime()
        set(prayershowhide) {
            field = prayershowhide
            notifyPropertyChanged(BR.prayershowhide)
        }


    @get:Bindable
    var showClearFilter: Int = View.GONE
        set(city) {
            field = city
            notifyPropertyChanged(BR.showClearFilter)
        }


    fun openFragment2(postAdModel: Feed) {
        val intentNext = Intent(activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.newsurl)
        activity.startActivity(intentNext)
    }

    fun openFragment3(postAdModel: Feed, position: Int) {
        val intentNext = Intent(activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.homeurl)
        activity.startActivity(intentNext)
    }

    fun openShare(postAdModel: Feed, position: Int) {
      openChooser(postAdModel, activity)
    }


    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {

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

        //  if(!isUpdated) {
        talentProfilesList.add(0, adModel);
        //  talentProfilesList.add(adModel)
        //  }
        // }
    }

    //var isUpdated = false

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

            Log.w(TAG, "Listen querySnapshot end" + querySnapshot.size())

            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]

            query = query.startAfter(lastVisible)


            for (change in querySnapshot.documentChanges) {

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else {
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


    fun doGetTalentsHeadline() {

        Log.d(TAG, "DOIT doGetTalents:")

        // talentProfilesList.clear()
        queryHeadline.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
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

            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]

            query = query.startAfter(lastVisible)


            for (change in querySnapshot.documentChanges) {

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else {
                    "server"
                }
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city new: ")
                    addHeadlineItems(change.document)
                }

                if (change.type == DocumentChange.Type.MODIFIED) {
                    Log.d(TAG, "New city modified: ")
                }
                Log.d(TAG, "Data fetched from $source")
            }
        }
    }

    fun addHeadlineItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Feed::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.imgurl)

        //  if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) && (adModel.eventState.ordinal == EventStatus.SHOWING.ordinal)) {


        //  getKeyWords(talentProfilesList,adModel)

        //  if(!isUpdated) {
        talentHeadlineList.add(0, adModel);
        //  talentProfilesList.add(adModel)
        //   }
        // }
    }

}


//
