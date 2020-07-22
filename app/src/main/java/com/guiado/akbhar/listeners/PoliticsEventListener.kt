package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.PoliticsViewModel

interface PoliticsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : PoliticsViewModel, position: Int)
    fun launchNews(countriesViewModel : PoliticsViewModel, position: Int)


}