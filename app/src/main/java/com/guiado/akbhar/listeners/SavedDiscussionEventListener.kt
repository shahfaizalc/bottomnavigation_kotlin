package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.SavedDiscussionModel

interface SavedDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SavedDiscussionModel, position: Int)


}