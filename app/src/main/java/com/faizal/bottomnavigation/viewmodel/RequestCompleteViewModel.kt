package com.faizal.bottomnavigation.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.adapter.RatingsAdapter
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.model2.Reviews
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentRequestComplete
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class RequestCompleteViewModel(internal val activity: FragmentActivity,
                               internal val fragmentProfileInfo: FragmentRequestComplete,
                               internal val postAdObj: String,
                               internal val adDocid: String?) : BaseObservable() {
    private val mAuth: FirebaseAuth

    companion object {
        private val TAG = "RequestComplete  "
    }

    var profile: Profile

    var ratings: Float = 1.0f
    var adapter = RatingsAdapter()


    @get:Bindable
    var userIds: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }


    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }


    init {
        profile = (GenericValues().getProfile(postAdObj, fragmentProfileInfo.context!!))
        mAuth = FirebaseAuth.getInstance()
        getVal()
    }


    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun getVal() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("review");
        query.whereEqualTo("userId", "Kq2vQqdWlCTsy5BKTFcjCnc88a62")//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {
                        Log.d(TAG, "Error getting saanu: " )
                        var listOfRating = emptyList<String>().toMutableList()

                        for (document in task.result!!) {
                            Log.d(TAG, "Error getting saanu: " +document.id)
                            if(document.id == mAuth.uid ){
                                review = document.get("review").toString()
                            }
                            listOfRating.add(document.get("review").toString())
                        }
                        userIds.value = listOfRating


                    } else {
                        Log.d(TAG, "Error getting saanu: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }


    fun updateReview() = View.OnClickListener {

        if (!handleMultipleClicks()) {

            val reviews: Reviews = Reviews()
            reviews.userId = adDocid
            reviews.ratedBy = mAuth.uid
            reviews.review = review
            val firbaseWriteHandler = FirbaseWriteHandler(fragmentProfileInfo).updateReview(reviews, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                    Toast.makeText(fragmentProfileInfo.context, fragmentProfileInfo.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess() {
                    Log.d(TAG, "DocumentSnapshot onSuccess ")
                }
            })

        }
    }

    fun updateRating() = View.OnClickListener {
    }
}


