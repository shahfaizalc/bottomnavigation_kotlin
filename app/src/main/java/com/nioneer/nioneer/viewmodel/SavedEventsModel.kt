package com.nioneer.nioneer.viewmodel

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.Events.MyCustomEvent
import com.nioneer.nioneer.R
import com.nioneer.nioneer.model2.*
import com.nioneer.nioneer.util.*
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.nioneer.nioneer.model.EventStatus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SavedEventsModel(internal val fragmentProfileInfo: FragmentSavedEvents)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Events>
    var query : Query
    var db :FirebaseFirestore


    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }

    init {
        mAuth = FirebaseAuth.getInstance()
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        query = db.collection("events")
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10).whereArrayContains("bookmarkBy",mAuth.currentUser!!.uid)

        doGetTalents()
    }

    @get:Bindable
    var finderTitle: String? = fragmentProfileInfo.resources.getString(R.string.finderEventTitle)
        set(city) {
            field = city
            notifyPropertyChanged(BR.finderTitle)
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


    fun openFragment2(postAdModel: Events, position: Int) {
        if(postAdModel.eventState.ordinal < EventStatus.HIDDEN.ordinal) {

//            val fragment = FragmentEvent()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
//            fragment.setArguments(bundle)
//            fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));
//
            val intent = Intent(fragmentProfileInfo, FragmentEvent::class.java)
            intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
            fragmentProfileInfo.startActivity(intent);
        } else {
            showPopUpWindow();
        }
    }

    fun showPopUpWindow(){
        val view = getNotificationContentView(fragmentProfileInfo,
                fragmentProfileInfo.applicationContext.resources.getString(R.string.oops_title),
                fragmentProfileInfo.applicationContext.resources.getString(R.string.oops_msg))
        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        view.findViewById<View>(R.id.closeBtn).setOnClickListener{
            popupWindow.dismiss()

        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    private fun getCommbinationWords(s: String): List<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1
    }

    fun doGetTalentsSearch(searchQuery: String) {
        query = db.collection("events")
                .whereArrayContainsAny("searchTags", getCommbinationWords(searchQuery).toList())
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10).whereArrayContains("bookmarkBy",mAuth.currentUser!!.uid)


        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Events::class.java)
        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

        getKeyWords(talentProfilesList,adModel)

        if(!isUpdated) {
            talentProfilesList.add(adModel)
        }

    }

    var isUpdated = false

    private fun getKeyWords(keyWords: ObservableArrayList<Events>,keyWord: Events): ObservableArrayList<Events> {

        isUpdated = false

        var count = 0;

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedDate.equals(keyWord.postedDate)){
                        isUpdated = true
                        talentProfilesList.set(count,keyWord)
                        return@notNull
                    }
                    count = count + 1;
                }
            }
        }
        return keyWords;
    }
    fun doGetTalents() {

        Log.d(TAG, "DOIT doGetTalents: ")

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

            Log.d(TAG, "Listen querySnapshot end"+querySnapshot.size())


            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
            query = query.startAfter(lastVisible)

            for (change in querySnapshot.documentChanges) {
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city: ${change.document.data}")
                }

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else{
                    "server"
                }
                Log.d(TAG, "Data fetched from $source")
                addTalentsItems(change.document)


            }
        }
    }



//    @Override
//    fun onNextButtonClick() = View.OnClickListener() {
//
//        val fragment = FragmentNewEvent()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));
//
//    }

//    @get:Bindable
//    var membersCount: Int? = isBookmarked()
//        set(city) {
//            field = city
//            notifyPropertyChanged(BR.membersCount)
//        }

//    fun isBookmarked(postDiscussion: Groups): Int? {
//        var isFollow = false
//        postDiscussion.members.notNull {
//            val likes: MutableIterator<Bookmarks> = it.iterator()
//            while (likes.hasNext()) {
//                val name = likes.next()
//                if (name.markedById.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
//                    isFollow = true
//                }
//            }
//        }
//
//        return isFollow
//    }
}
