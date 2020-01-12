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
    var query : Query
    var db :FirebaseFirestore

    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }


    init {
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        query = db.collection("groups").orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
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

        Log.d(TAG, "DOIT doGetTalents: ")

        // talentProfilesList.clear()
        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen error", e)
                return@addSnapshotListener
            }
            Log.d(TAG, "DOIT doGetTalents: "+querySnapshot?.size())

            if(querySnapshot!!.size() <= 0){
                Log.w(TAG, "Listen querySnapshot end")
                return@addSnapshotListener

            }

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


    private fun getCommbinationWords(s: String): List<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1
    }

    fun doGetTalentsSearch(searchQuery: String) {
        query = db.collection("groups")
                .whereArrayContainsAny("searchTags", getCommbinationWords(searchQuery).toList())
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Groups::class.java)

        Log.d(TAG, "Success getting documents:groups " + adModel.postedBy)
        if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) ) {
            talentProfilesList = getKeyWords(talentProfilesList,adModel)
            talentProfilesList.add(adModel)
        }

    }
    private fun getKeyWords(keyWords: ObservableArrayList<Groups>,keyWord: Groups): ObservableArrayList<Groups> {

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedBy!!.equals(keyWord.postedBy)) {
                        keyWords.remove(value)
                        return@notNull
                    }
                }
            }
        }
        return keyWords;
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
