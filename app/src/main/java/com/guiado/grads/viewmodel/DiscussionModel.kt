package com.guiado.grads.viewmodel


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.model2.Bookmarks
import com.guiado.grads.model2.PostDiscussion
import com.guiado.grads.model2.Profile
import com.guiado.grads.util.*
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FirestoreDisccussFragmment
import com.guiado.grads.view.FragmentDiscussions
import com.guiado.grads.view.FragmentNewDiscusssion
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class DiscussionModel(internal var activity: FragmentActivity,
                      internal val fragmentProfileInfo: FragmentDiscussions)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<PostDiscussion>


    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }


    init {
        talentProfilesList = ObservableArrayList()
        mAuth = FirebaseAuth.getInstance()
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
        val fragment = FirestoreDisccussFragmment()
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
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun compareLIt(s: String): Set<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1.intersect(searchTags)
    }

    fun doGetTalentsSearch(searchQuery: String) {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("discussion").whereArrayContainsAny("searchTags", compareLIt(searchQuery).toList())
        Log.d(TAG, "DOIT doGetTalentsSearch: ")

        query.get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        talentProfilesList.clear()
                        for (document in task.result!!) {
                            addTalentsItems(document)
                        }
                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(PostDiscussion::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

         if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) ) {
             talentProfilesList.add(adModel)
         }
    }


    fun doGetTalents() {

        val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = firestoreSettings
        Log.d(TAG, "DOIT doGetTalents: ")

        talentProfilesList.clear()
        val query = db.collection("discussion")
        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(DiscussionModel.TAG, "Listen error", e)
                return@addSnapshotListener
            }

            for (change in querySnapshot!!.documentChanges) {
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(DiscussionModel.TAG, "New city: ${change.document.data}")
                }

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else{
                    "server"
                }
                Log.d(DiscussionModel.TAG, "Data fetched from $source")
                addTalentsItems(change.document)


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
