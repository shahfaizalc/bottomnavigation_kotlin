package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.SavedDiscussionModel

interface SavedDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SavedDiscussionModel, position: Int)


}