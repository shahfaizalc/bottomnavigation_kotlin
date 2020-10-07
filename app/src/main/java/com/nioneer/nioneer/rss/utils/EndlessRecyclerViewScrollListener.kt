package com.nioneer.nioneer.rss.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *  Class to provide endless recyclerView scrollListener
 */
abstract class EndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager)
    : RecyclerView.OnScrollListener() {

    /**
     * Visible Threshold
     */
    private val visibleThreshold = 5

    /**
     * Current Page
     */
    private var currentPage = 0

    /**
     * Previous total item count
     */
    private var previousTotalItemCount = 0

    /**
     * Loading
     */
    private var loading = true

    /**
     * Layout manager
     */
    private val mLayoutManager: RecyclerView.LayoutManager

    init {
        this.mLayoutManager = layoutManager as RecyclerView.LayoutManager
    }

    /**
     * On list view scrolled
     */
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount
        lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    // for loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)

}