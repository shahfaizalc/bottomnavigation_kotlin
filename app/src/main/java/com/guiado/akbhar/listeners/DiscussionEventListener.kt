package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel

interface DiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DiscussionModel, position: Int)
    fun launchNews(countriesViewModel : DiscussionModel, position: Int)


}