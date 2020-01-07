package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.SavedDiscussionModel

interface SavedDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SavedDiscussionModel, position: Int)


}