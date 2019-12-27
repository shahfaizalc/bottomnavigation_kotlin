package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}