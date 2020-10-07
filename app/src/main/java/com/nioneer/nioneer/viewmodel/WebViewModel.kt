package com.nioneer.nioneer.viewmodel

import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.listeners.WebViewCallback
import com.nioneer.nioneer.rss.utils.MyWebViewClient

/**
 * The view model class to show the blog article in a web view
 *
 */
class WebViewModel : BaseObservable(), WebViewCallback {

    /**
     * TAG: class name
     */
    private val TAG = "WebViewModel"

    /**
     * Web view  url
     */
    var webViewUrl = ""

    /**
     * To show progress : webview onload progress
     */
    @get:Bindable
    var progressBarVisible = View.GONE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    /**
     * User Notification  visibility
     */
    @get:Bindable
    var msgView = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msgView)
        }

    /**
     * User Notification  text
     */
    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    /**
     * To avail WebViewClient
     */
    @Bindable
    fun getWebViewClient(): WebViewClient {
        progressBarVisible = View.VISIBLE
        return MyWebViewClient(this)
    }

    /**
     * On  webview onLoad success
     */
    override fun onSuccess() {
        Log.d(TAG, "onSuccess: Webpage loading successful")
        progressBarVisible = View.GONE
    }

    /**
     * On webview onLoad failed
     * @param err : Error  message
     */
    override fun onError(err: String) {
        Log.d(TAG, "onError: Webpage loading failed: Error " + err)
        progressBarVisible = View.GONE
    }
}