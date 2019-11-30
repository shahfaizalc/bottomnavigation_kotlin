package com.faizal.bottomnavigation.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.model2.Reviews
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentRequestComplete
import com.google.firebase.auth.FirebaseAuth

class RequestCompleteViewModel(internal val activity: FragmentActivity,
                               internal val fragmentProfileInfo: FragmentRequestComplete,
                               internal val postAdObj: String,
                               internal val adDocid: String?) : BaseObservable() {
    private val mAuth: FirebaseAuth

    companion object {
        private val TAG = "RequestComplete"
    }

    var profile: Profile

    var ratings: Float = 1.0f



    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }


    init {
        profile = (GenericValues().getProfile(postAdObj, fragmentProfileInfo.context!!))
        mAuth = FirebaseAuth.getInstance()
    }


    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun updateReview() = View.OnClickListener {
        val reviews : Reviews = Reviews()
        reviews.userId = adDocid
        reviews.ratedBy = mAuth.uid
        reviews.review = "saanvvi"
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

    fun updateRating() = View.OnClickListener {
    }
}


