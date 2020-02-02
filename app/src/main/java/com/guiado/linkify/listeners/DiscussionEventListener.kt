package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}