package com.guiado.akbhar.viewmodel

import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.guiado.akbhar.R
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.view.FragmentIntro
import javax.inject.Inject
import com.guiado.akbhar.BR
import com.guiado.akbhar.model.NewsProviders


class IntroViewModel(private val fragmentProfile: FragmentIntro) : BaseObservable() {

    var isOnline: Boolean = false

    var networkStateHandler: NetworkChangeHandler

    var channelTamilMovieReviewDataModel: ArrayList<NewsProviders>

    @get:Bindable
    var imgUrl: String? = null
        set(imgURL) {
            field = imgURL
            notifyPropertyChanged(BR.imgUrl)
        }
    private var mLastClickTime: Long = 0

    init {
        channelTamilMovieReviewDataModel = ObservableArrayList()
        networkStateHandler=  NetworkChangeHandler()
        fetchUserProfilePic()
    }

    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    private fun handleMultipleClicks(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }


    private fun fetchUserProfilePic() {
        Log.d(TAG, "getDownload Url succcess")
        imgUrl = "uri.toString()"

    }

    fun openFragment(contentModelObj: String) {

        Log.d("userClicked  ", "" + contentModelObj);
    }

    companion object {
        private val TAG = "ProfileViewModel"
    }

}