package com.nioneer.nioneer.viewmodel


import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.Events.MyCustomEvent
import com.nioneer.nioneer.R
import com.nioneer.nioneer.model.CoachItem
import com.nioneer.nioneer.model.IndiaItem
import com.nioneer.nioneer.model2.PostEvents
import com.nioneer.nioneer.model2.Profile
import com.nioneer.nioneer.util.GenericValues
import com.nioneer.nioneer.util.MultipleClickHandler
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.view.FragmentAdSearch
import com.nioneer.nioneer.view.FragmentKeyWords
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

class AdSearchModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentAdSearch)// To show list of user images (Gallery)
    : BaseObservable() {

    var eventsList: ObservableArrayList<PostEvents>
    var talentProfilesList: ObservableArrayList<Profile>

    var ratings: ObservableArrayList<String>

    var searchQuery = ""
    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }

    var cityCode: String? = "0"

    init {
        readAutoFillItems()
        eventsList = ObservableArrayList()
        talentProfilesList = ObservableArrayList()
        ratings = ObservableArrayList()
        mAuth = FirebaseAuth.getInstance()

    }

    @get:Bindable
    var roleAdapterAddress: ArrayList<IndiaItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }

    private fun readAutoFillItems() {
        val c = GenericValues()
        roleAdapterAddress = c.readAutoFillItems(activity.applicationContext)
    }

    @get:Bindable
    var city: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)
        }
    @get:Bindable
    var finderTitle: String? = activity.resources.getString(R.string.finderEventTitle)
        set(city) {
            field = city
            notifyPropertyChanged(BR.finderTitle)
        }

    var isEvent = true

    fun onSplitTypeChanged(radioGroup: RadioGroup, id: Int) {
        when((id)){
            R.id.radio1->{
                finderTitle = activity.resources.getString(R.string.finderEventTitle)
                isEvent = true
            }
            R.id.radio2 ->{
                finderTitle = activity.resources.getString(R.string.finderPersonTitle)
                isEvent = false
            }
        }
    }

    @Override
    fun searchAdClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            if (city.isNullOrEmpty()) {
                Log.d(TAG, "Fill all values ")

            } else if(isEvent){
                doGetEvents()
            } else {
                doGetTalents()
            }

        }
    }

    @Override
    fun searchKeyWords() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            openKeyWordsFragment()

        }
    }

    fun openKeyWordsFragment() {
        EventBus.getDefault().register(this);
//        val fragment = FragmentKeyWords()
//        val bundle = Bundle()
        val profile = Profile()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

        val intent = Intent(fragmentProfileInfo.activity,FragmentKeyWords::class.java)
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragmentProfileInfo.activity!!.startActivity(intent)
    }

    /*
      Method will act as the event handler for MyCustomEvent.kt
      */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        val profile = event.data
        keyyWords = getKeyWords(profile.keyWords)

    }

    private fun getKeyWords(keyWords: MutableList<Int>?): String {

        val c = GenericValues()
        var listOfCoachings: ArrayList<CoachItem>? = null
        listOfCoachings = c.readCoachItems(fragmentProfileInfo.context!!)

        var result = ""

        val numbersIterator = keyWords!!.iterator()
        numbersIterator.let {
            while (numbersIterator.hasNext()) {
                val value = (numbersIterator.next())
                result += " " + listOfCoachings.get(value - 1).categoryname + ", "
            }
        }

        return result;
    }

    @get:Bindable
    var keyyWords: String? = ""
        set(city) {
            field = city
            notifyPropertyChanged(BR.keyyWords)
        }


    fun openFragment(postAdModel: PostEvents, position: Int) {
//        val fragment = FragmentRequestComplete()
//        val bundle = Bundle()
//        bundle.putString(Constants.AD_DOCID, ratings.get(position));
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(postAdModel))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

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


    fun doGetEvents() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("events");
        query.whereEqualTo("address.city", city)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        talentProfilesList.clear()
                        eventsList.clear()
                        for (document in task.result!!) {
                            addEventsItems(document)
                        }
                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }

    fun addEventsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(PostEvents::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.title)

        eventsList.add(adModel)
        ratings.add(document.id)

        Log.d(TAG, "Success getting : " + document.id)


//        if (adModel.userId.equals(mAuth.currentUser!!.uid) && adModel.title!!.contains(searchQuery)) {
//
//            countriesInfoModel.add(adModel)
//        }
    }

    fun doGetTalents() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("userinfo");
        query.whereEqualTo("address.city", city)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        eventsList.clear()
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
        ratings.add(document.id)

        Log.d(TAG, "Success getting : " + document.id)


//        if (adModel.userId.equals(mAuth.currentUser!!.uid) && adModel.title!!.contains(searchQuery)) {
//
//            countriesInfoModel2.add(adModel)
//        }
    }
}
