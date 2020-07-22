package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.TravelViewModel

interface TravelEventListener {

    fun onClickAdSearchListItem(countriesViewModel : TravelViewModel, position: Int)
    fun launchNews(countriesViewModel : TravelViewModel, position: Int)


}