package com.guiado.akbhar.viewmodel

import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.view.FragmentNewsProviders
import com.guiado.akbhar.BR
import com.guiado.akbhar.model.NewsProviders
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.WebViewActivity


class NewsProvidersViewModel(private val fragmentProfile: FragmentNewsProviders) : BaseObservable() {

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
        val intentNext = Intent(fragmentProfile.activity, WebViewActivity::class.java)
        intentNext.putExtra(Constants.POSTAD_OBJECT, contentModelObj)
        fragmentProfile.activity!!.startActivity(intentNext)
    }

    companion object {
        private val TAG = "ProfileViewModel"
    }

}