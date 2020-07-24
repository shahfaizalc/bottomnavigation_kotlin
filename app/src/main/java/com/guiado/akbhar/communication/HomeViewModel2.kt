package com.guiado.akbhar.communication

import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.guiado.akbhar.BR

class HomeViewModel2 : ViewModelCallback() {

    /**
     * TAG: class name
     */
    private val TAG = "HomeViewModel"


    /**
     * List of Blog Articles
     */

    /**
     * List of Blog Aricles filtered by title key words
     */
    var blogArticlesFilteredListModel: ObservableArrayList<ClubsModel2>

    private var mLastClickTime: Long = 0


    init {
        blogArticlesFilteredListModel = ObservableArrayList()
    }



}