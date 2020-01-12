package com.guiado.linkify.viewmodel


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.guiado.linkify.BR
import com.guiado.linkify.Events.MyCustomEvent
import com.guiado.linkify.R
import com.guiado.linkify.model2.PostDiscussion
import com.guiado.linkify.model2.Profile
import com.guiado.linkify.util.*
import com.guiado.linkify.utils.Constants
import com.guiado.linkify.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyDiscussionModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentMyDiscussions)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<PostDiscussion>

    var query : Query
    var db :FirebaseFirestore
    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }


    init {
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        query = db.collection("discussion")
                .orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
                .whereEqualTo("postedBy",mAuth.currentUser!!.uid)
        doGetTalents()
    }



    @get:Bindable
    var finderTitle: String? = activity.resources.getString(R.string.finderEventTitle)
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


    fun openFragment2(postAdModel: PostDiscussion, position: Int) {
        val fragment = FirestoreMyDisccussFragmment()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().discussionToString(postAdModel))
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

        val fragment = FragmentNewDiscusssion()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

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
                .limit(10).whereEqualTo("postedBy",mAuth.currentUser!!.uid)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(PostDiscussion::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

       if (adModel.postedBy.equals(mAuth.currentUser!!.uid)) {

            talentProfilesList = getKeyWords(talentProfilesList,adModel)

            talentProfilesList.add(adModel)
        }
    }


    private fun getKeyWords(keyWords: ObservableArrayList<PostDiscussion>,keyWord: PostDiscussion): ObservableArrayList<PostDiscussion> {

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedDate.equals(keyWord.postedDate)){
                        keyWords.remove(value)
                        return@notNull
                    }
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
            query = query.startAfter(lastVisible).whereEqualTo("postedBy",mAuth.currentUser!!.uid)

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

}
