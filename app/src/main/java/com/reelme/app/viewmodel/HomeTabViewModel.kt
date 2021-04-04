package com.reelme.app.viewmodel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.viewpager.widget.PagerAdapter
import com.reelme.app.BR
import com.reelme.app.adapter.ViewPagerAdapter

class HomeTabViewModel : BaseObservable() {

    /**
     * TAG
     */
    private val TAG = "HomeViewModel"

    /**
     * View Pager Adapter
     */
    private var adapter: ViewPagerAdapter? = null

    val pagerAdapter: PagerAdapter?
        @Bindable
        get() = adapter

    fun setPagerAdapter(adapter: ViewPagerAdapter) {
        Log.d(TAG, "setPagerAdapter")
        this.adapter = adapter
        notifyPropertyChanged(BR.pagerAdapter)
    }
}
