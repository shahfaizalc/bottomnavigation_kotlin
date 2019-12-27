package com.faizal.bottomnavigation.viewmodel


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model2.*
import com.faizal.bottomnavigation.util.*
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TheEventsModel(internal var activity: FragmentActivity,
                     internal val fragmentProfileInfo: FragmentTheEvents)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Events>


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


    fun openFragment2(postAdModel: Events, position: Int) {
        val fragment = FragmentEvent()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun doGetTalents() {

       val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = firestoreSettings
        val query = db.collection("events");
        query.get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        talentProfilesList.clear()
                        for (document in task.result!!) {
                            addTalentsItems(document)
                        }
                    } else {
                        Log.d(TAG, "Error getting documentss:groups " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }

    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Events::class.java)

        Log.d(TAG, "Success getting documents:groups " + adModel.postedBy)

            talentProfilesList.add(adModel)

    }

    fun doGetTalentsSearch(searchQuery:String) {
        val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = firestoreSettings
        val query = db.collection("events").whereArrayContainsAny("searchTags",compareLIt(searchQuery).toList())

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


    @Override
    fun onNextButtonClick() = View.OnClickListener() {

        val fragment = FragmentNewEvent()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

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
