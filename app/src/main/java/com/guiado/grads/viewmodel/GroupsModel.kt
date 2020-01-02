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
import com.guiado.grads.model2.*
import com.guiado.grads.util.*
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GroupsModel(internal var activity: FragmentActivity,
                  internal val fragmentProfileInfo: FragmentGroups)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Groups>


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


    fun openFragment2(postAdModel: Groups, position: Int) {
        val fragment = FragmentJoinGroup()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().groupToString(postAdModel))
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

        val fragment = FragmentNewGroup()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

    }

    fun doGetTalents() {

        val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        Log.d(TAG, "DOIT doGetTalents: ")

        talentProfilesList.clear()
        val query = db.collection("groups")
        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen error", e)
                return@addSnapshotListener
            }

            for (change in querySnapshot!!.documentChanges) {
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

    fun doGetTalentsSearch(searchQuery:String) {
        val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }

        val query = db.collection("groups").whereArrayContainsAny("searchTags",compareLIt(searchQuery).toList())

        query.get()
                .addOnCompleteListener({ task ->
                    val any = if (task.isSuccessful) {
                        talentProfilesList.clear()
                        for (document in task.result!!) {
                            addTalentsItems(document)
                        }
                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener( { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener( { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }

    private fun compareLIt(s:String): List<String> {
        return s.sentenceToWords()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Groups::class.java)

        Log.d(TAG, "Success getting documents:groups " + adModel.postedBy)
        if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) ) {
            talentProfilesList.add(adModel)
        }

    }


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
