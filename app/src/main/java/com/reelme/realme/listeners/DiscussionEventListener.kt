package com.reelme.realme.listeners

import com.reelme.realme.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}