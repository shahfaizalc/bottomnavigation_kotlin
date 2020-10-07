package com.nioneer.nioneer.viewmodel

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.model.BlogItemModel
import com.nioneer.nioneer.rss.utils.ViewModelCallback


/**
 * The view model class to show the list of blog articles
 */

class RssHomeViewModel : ViewModelCallback() {

    /**
     * TAG: class name
     */
    private val TAG = "RssHomeViewModel"

    /**
     * Specific Blog article link
     */
    private val weblinkBlogArticle = MutableLiveData<String>()

    /**
     * List of Blog Articles
     */
    var blogArticlesListModel: ObservableArrayList<BlogItemModel>

    /**
     * List of Blog Aricles filtered by title key words
     */
    var blogArticlesFilteredListModel: ObservableArrayList<BlogItemModel>

    private var mLastClickTime: Long = 0


    init {
        blogArticlesListModel = ObservableArrayList()
        blogArticlesFilteredListModel = ObservableArrayList()
    }

    /**
     * To show progress on list loading
     */
    @get:Bindable
    var progressBarVisible = View.GONE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    /**
     *  User Notification  visibility
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
     * To reload the blog. if it has failed to load
     */
    @get:Bindable
    var onLoadErrorMsgText: String? = null
        set(blogRetry) {
            field = blogRetry
            notifyPropertyChanged(BR.onLoadErrorMsgText)
        }

    /**
     * To show hint to  user
     */
    @get:Bindable
    var onLoadErrorMsgVisibility = View.VISIBLE
        set(screenHint) {
            field = screenHint
            notifyPropertyChanged(BR.onLoadErrorMsgVisibility)
        }

    /**
     * On articles list item clicked
     */
    fun doUpdateClickedItem(blogItemModel: BlogItemModel) {
        Log.d(TAG, "doUpdateClickedItem: user clicked on " + blogItemModel.title);
        if(!handleMultipleClicks()) {
            weblinkBlogArticle.value = blogItemModel.link
        }
    }

    /**
     * To get article url
     */
    fun getBlogArticleLink(): LiveData<String> {
        return weblinkBlogArticle
    }

    /**
     * Method to handle multiple click on the item with in short span of time.
     */
    private fun handleMultipleClicks(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }
}
