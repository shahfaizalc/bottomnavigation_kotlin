package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)


}