package com.guiado.akbhar.viewmodel

import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.google.firebase.firestore.*
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.BR
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.model.LanguageRegionEnum
import com.guiado.akbhar.model.Magazines
import com.guiado.akbhar.model.RegionEnum
import com.guiado.akbhar.util.firestoreSettings
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.utils.SharedPreference
import com.guiado.akbhar.view.FragmentMagazine
import com.guiado.akbhar.view.WebViewActivity


class MagazineViewModel(private val fragmentProfile: FragmentMagazine) : BaseObservable() {

    var isOnline: Boolean = false

    var networkStateHandler: NetworkChangeHandler

    var channelTamilMovieReviewDataModel: ArrayList<Magazines>

    var talentProfilesList: ObservableArrayList<Feed>

    var query: Query

    var db: FirebaseFirestore
    var resetScrrollListener: Boolean = false;


    init{
        talentProfilesList = ObservableArrayList()

        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e: Exception) {
            Log.d(TAG, "getProfile  " + e)
        }
        var pref = SharedPreference(fragmentProfile.context!!).getValueString(Constants.LANGUAGE_ID)
        Log.d(TAG, "getProfile  " + pref)

        if (pref!!.isEmpty()) {
            pref = LanguageRegionEnum.FR.name
        }

       // query = db.collection("/NEWS/news_arabic/editorials").whereEqualTo(Constants.LANGUAGE_ID, pref).orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(20)
        query = db.collection("/NEWS/news_arabic/world").whereEqualTo(Constants.LANGUAGE_ID, pref).orderBy("growZoneNumber", Query.Direction.DESCENDING).limit(20)

        doGetTalents()

    }

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


    fun openFragment2(postAdModel: Feed) {
        val intentNext = Intent(fragmentProfile.activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.newsurl)
        fragmentProfile.activity!!.startActivity(intentNext)
    }

    fun openFragment3(postAdModel: Feed, position: Int) {
        val intentNext = Intent(fragmentProfile.activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, postAdModel.homeurl)
        fragmentProfile.activity!!.startActivity(intentNext)
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



    @get:Bindable
    var imgUrl: String? = null
        set(imgURL) {
            field = imgURL
            notifyPropertyChanged(BR.imgUrl)
        }
    private var mLastClickTime: Long = 0

    init {
        channelTamilMovieReviewDataModel = ObservableArrayList()
        networkStateHandler=  NetworkChangeHandler()
        fetchUserProfilePic()
    }

    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    private fun handleMultipleClicks(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }


    private fun fetchUserProfilePic() {
        Log.d(TAG, "getDownload Url succcess")
        imgUrl = "uri.toString()"

    }

    fun openFragment(contentModelObj: String) {

        Log.d("userClicked  ", "" + contentModelObj);
        val intentNext = Intent(fragmentProfile.activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, contentModelObj)
        fragmentProfile.activity!!.startActivity(intentNext)
    }

    companion object {
        private val TAG = "ProfileViewModel"
    }

}