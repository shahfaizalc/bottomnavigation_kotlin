package com.guiado.akbhar.viewmodel


import android.content.Intent
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.guiado.akbhar.BR
import com.guiado.akbhar.Events.MyCustomEvent
import com.guiado.akbhar.R
import com.guiado.akbhar.model2.Events
import com.guiado.akbhar.model2.Profile
import com.guiado.akbhar.util.*
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.FragmentMyEvent
import com.guiado.akbhar.view.FragmentMyEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyEventsModel(
        internal val fragmentProfileInfo: FragmentMyEvents)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Events> = ObservableArrayList()
    lateinit var query: Query
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {

        private val TAG = "MyEventsModel"
    }

    init {
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e: Exception) {
            Log.d(TAG, "getProfile  " + e)
        }
        query = db.collection("events").orderBy("postedDate", Query.Direction.DESCENDING).limit(10).whereEqualTo("postedBy", mAuth.currentUser!!.uid)
        doGetTalents()
        Log.d(TAG, "getProfile  intialize")

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

//        val fragment = FragmentMyEvent()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

        val intent = Intent(fragmentProfileInfo, FragmentMyEvent::class.java)
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
        fragmentProfileInfo.startActivityForResult(intent, 10)

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
                .orderBy("postedDate", Query.Direction.DESCENDING).whereEqualTo("postedBy", mAuth.currentUser!!.uid)
                .limit(10)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Events::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

        if (adModel.postedBy.equals(mAuth.currentUser!!.uid)) {

            getKeyWords(talentProfilesList, adModel)

            if (!isUpdated) {
                talentProfilesList.add(adModel)
            } else {

                for (user in talentProfilesList) {
                    if (user.postedBy.equals(adModel.postedBy) && user.postedDate.equals(adModel.postedDate)) {
                        user.eventState = adModel.eventState
                        break
                    }
                }
            }
        }
    }

    var isUpdated = false

    private fun getKeyWords(keyWords: ObservableArrayList<Events>, keyWord: Events): ObservableArrayList<Events> {

        isUpdated = false

        var count = 0;

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedDate.equals(keyWord.postedDate)) {
                        isUpdated = true
                        talentProfilesList.set(count, keyWord)
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
            Log.d(TAG, "DOIT doGetTalents: " + querySnapshot?.size())


            if (querySnapshot == null) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            if (querySnapshot.size() < 1) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            Log.d(TAG, "Listen querySnapshot end" + querySnapshot.size())

            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
            try {

                query = query.startAfter(lastVisible).whereEqualTo("postedBy", mAuth.currentUser!!.uid)

                for (change in querySnapshot.documentChanges) {
                    if (change.type == DocumentChange.Type.ADDED) {
                        Log.d(TAG, "New city: ${change.document.data}")
                    }

                    val source = if (querySnapshot.metadata.isFromCache) {
                        "local cache"
                    } else {
                        "server"
                    }
                    Log.d(TAG, "Data fetched from $source $change.type")
                    addTalentsItems(change.document)


                }
            } catch (e: Exception) {
                Log.d(TAG, "New city: ${e.message}")

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
