package com.faizal.bottomnavigation.viewmodel


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model.IndiaItem
import com.faizal.bottomnavigation.model2.PostEvents
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentAdSearch
import com.faizal.bottomnavigation.view.FragmentKeyWords
import com.faizal.bottomnavigation.view.FragmentMyAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class MyAdsModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentMyAds)// To show list of user images (Gallery)
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
        query//.whereEqualTo("showDate", showDate)
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
