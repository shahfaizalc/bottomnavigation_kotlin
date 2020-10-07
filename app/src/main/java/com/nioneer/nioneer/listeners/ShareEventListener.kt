package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.RssHomeViewModel

/**
 * Interface to notify share event
 */
interface ShareEventListener {

    /**
     * On Blog list item share clicked
     * @param rssHomeViewModel : Rss home view model
     * @param position : item position
     */

    fun onClickShareItem(listModel: RssHomeViewModel, position: Int)

}