package com.faizal.bottomnavigation.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentRequestComplete

class PostRequestViewModel(internal val activity: FragmentActivity,
                           internal val fragmentProfileInfo: FragmentRequestComplete,
                           internal val postAdObj: String) : BaseObservable() {
    companion object {

        private val TAG = "ProfileGalleryViewModel"
    }

    var profile: Profile

    init {
        profile = (GenericValues().getProfile(postAdObj, fragmentProfileInfo.context!!))
    }
    
    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {

//         var myFirebaseMessagingService = MyFirebaseMessagingService();
//            myFirebaseMessagingService.sendNotification(null,activity.applicationContext)
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun updateKeyWords() = View.OnClickListener {
    }
}


