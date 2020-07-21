package com.guiado.akbhar.viewmodel


import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.guiado.akbhar.BR
import com.guiado.akbhar.Events.MyCustomEvent
import com.guiado.akbhar.R
import com.guiado.akbhar.model2.Profile
import com.guiado.akbhar.util.MultipleClickHandler
import com.guiado.akbhar.view.FragmentSimiliarDiscussion
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SimilarDiscussionModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentSimiliarDiscussion)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Profile>


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
    }


    fun openFragment2(postAdModel: Profile, position: Int) {
//        val fragment = FragmentRequestComplete()
//        val bundle = Bundle()
//        bundle.putString(Constants.AD_DOCID, ratings.get(position));
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(postAdModel))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }


    fun doGetTalents() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("events");
        query.whereEqualTo("postedBy", mAuth.currentUser!!.uid)
                .get()
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

        val adModel = document.toObject(Profile::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.title)

        talentProfilesList.add(adModel)

        Log.d(TAG, "Success getting : " + document.id)


//        if (adModel.userId.equals(mAuth.currentUser!!.uid) && adModel.title!!.contains(searchQuery)) {
//
//            countriesInfoModel2.add(adModel)
//        }
    }
}
