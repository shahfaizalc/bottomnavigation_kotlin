package com.faizal.bottomnavigation.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
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
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentRequestComplete
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.utils.DatePickerEvent

import java.util.*

class AdSearchModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentAdSearch)// To show list of user images (Gallery)
    : BaseObservable() {

    var countriesInfoModel: ObservableArrayList<PostAdModel>
    var countriesInfoModelFilter: ObservableArrayList<PostAdModel>
    var searchQuery = ""
    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }

    var cityCode: String? = "0"

    init {
        readAutoFillItems()
        countriesInfoModel = ObservableArrayList()
        countriesInfoModelFilter = ObservableArrayList()
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
    var showDate: String? = null
        set(showDate) {
            field = showDate
            notifyPropertyChanged(BR.showDate)
        }

    @get:Bindable
    var city: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)
        }

    @Override
    fun datePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentProfileInfo.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    showDate = result
                }
            })
        }
    }

    @Override
    fun searchAdClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            if (searchQuery.isEmpty() || showDate.isNullOrEmpty() || city.isNullOrEmpty()) {
                Log.d(TAG, "Fill all values ")

            } else {
                getVal()
            }

        }
    }

    fun openFragment(postAdModel: PostAdModel) {
        val fragment = FragmentRequestComplete()
        val bundle = Bundle()
        bundle.putParcelable(Constants.POSTAD_OBJECT, postAdModel)
        fragment.setArguments(bundle)
        fragmentProfileInfo.newInstance(1, fragmentProfileInfo, bundle);
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }


    fun getVal() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("ads");
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
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu.size()) })
    }

    fun addListItem(document: QueryDocumentSnapshot) {
        val adModel = document.toObject(PostAdModel::class.java)
        if (adModel.userId.equals(mAuth.currentUser!!.uid) && adModel.title!!.contains(searchQuery)) {

            countriesInfoModel.add(adModel)
        }
    }
}
