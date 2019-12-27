package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}