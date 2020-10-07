package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}