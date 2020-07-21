package com.guiado.akbhar.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.adapter.RatingsAdapter
import com.guiado.akbhar.listeners.EmptyResultListener
import com.guiado.akbhar.model2.Profile
import com.guiado.akbhar.model2.Reviews
import com.guiado.akbhar.network.FirbaseWriteHandler
import com.guiado.akbhar.util.*
import com.guiado.akbhar.view.FragmentRequestComplete
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

    var adapter = RatingsAdapter()


    @get:Bindable
    var userIds: MutableLiveData<List<Reviews>> = MutableLiveData<List<Reviews>>()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }


    @get:Bindable
    var reviewBtnVisisblity: Int? = View.VISIBLE
        set(city) {
            field = city
            notifyPropertyChanged(BR.reviewBtnVisisblity)
        }
    @get:Bindable
    var reviewBtnEnable: Boolean? = false
        set(city) {
            field = city
            notifyPropertyChanged(BR.reviewBtnEnable)
        }

    @get:Bindable
    var reviewbtnText: String? = fragmentProfileInfo.context?.getString(R.string.review_edit)
        set(city) {
            field = city
            notifyPropertyChanged(BR.reviewbtnText)
        }
    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }


    @get:Bindable
    var ratings: Float = 0.0f
        set(city) {
            field = city
            notifyPropertyChanged(BR.ratings)
        }

    @get:Bindable
    var tags: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.tags)
        }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
    }

    init {
        profile = (GenericValues().getProfile(postAdObj, fragmentProfileInfo.context!!))
        tags = getKeys(profile.keyWords,fragmentProfileInfo.context!!)
        mAuth = FirebaseAuth.getInstance()
        getVal()
    }


    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun getVal() {

        val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        val query = db.collection("review");
        query.get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Error getting saanu: " + task.result!!.size())
                        val listOfRating = emptyList<Reviews>().toMutableList()
                        for (document in task.result!!) {
                            Log.d(TAG, "Error getting saanu: " + document.id)
                            if (document.get("ratedBy").toString() == mAuth.uid) {
                                document.get("review").notNull { review = it.toString() }
                                document.get("rating").notNull { ratings = it.toString().toFloat() }
                            } else {
                                listOfRating.add(document.toObject(Reviews::class.java))
                            }
                        }
                        userIds.value = listOfRating


                    } else {
                        Log.d(TAG, "Error getting saanu: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }


    fun updateReview() = View.OnClickListener {

        if (reviewbtnText.equals(fragmentProfileInfo.context?.getString(R.string.review_edit))) {
            reviewBtnEnable = true;
            reviewbtnText = fragmentProfileInfo.context?.getString(R.string.review_post)

        } else if (!handleMultipleClicks()) {

            if (!review!!.isEmpty() && ratings != 0.0f) {

                val reviews: Reviews = Reviews()
                reviews.userId = adDocid
                reviews.ratedBy = mAuth.uid
                reviews.review = review
                reviews.rating = ratings.toString()
                reviews.date = System.currentTimeMillis().toString()
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentProfileInfo).updateReview(reviews, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                        Toast.makeText(fragmentProfileInfo.context, fragmentProfileInfo.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess ")
                    }
                })
            } else {
                Toast.makeText(fragmentProfileInfo.context, "Please Rate and Review", Toast.LENGTH_LONG).show()
            }
        }
    }

}


