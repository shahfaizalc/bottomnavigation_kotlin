package com.faizal.bottomnavigation.viewmodel


import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.model.IndiaItem
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.view.FragmentAdSearch
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentRequestComplete

import java.util.*

class AdSearchModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentAdSearch)// To show list of user images (Gallery)
    : BaseObservable() {

    var countriesInfoModel: ObservableArrayList<Profile>
    var ratings: ObservableArrayList<String>

    var searchQuery = ""
    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }

    var cityCode: String? = "0"

    init {
        readAutoFillItems()
        countriesInfoModel = ObservableArrayList()
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

    @Override
    fun searchAdClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            if (city.isNullOrEmpty()) {
                Log.d(TAG, "Fill all values ")

            } else {
                getVal()
            }

        }
    }

    fun openFragment(postAdModel: Profile, position: Int) {
        val fragment = FragmentRequestComplete()
        val bundle = Bundle()
        bundle.putString(Constants.AD_DOCID, ratings.get(position));
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(postAdModel))
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }


    fun getVal() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("userinfo");
        query.whereEqualTo("address.city", city)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        countriesInfoModel.clear()
                        for (document in task.result!!) {
                            addListItem(document)
                        }
                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }

    fun addListItem(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Profile::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.name)

        countriesInfoModel.add(adModel)
        ratings.add(document.id)

        Log.d(TAG, "Success getting : " + document.id)


//        if (adModel.userId.equals(mAuth.currentUser!!.uid) && adModel.title!!.contains(searchQuery)) {
//
//            countriesInfoModel.add(adModel)
//        }
    }
}
