package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.MyDiscussionModel
import com.guiado.grads.viewmodel.SavedDiscussionModel

interface SavedDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SavedDiscussionModel, position: Int)


}