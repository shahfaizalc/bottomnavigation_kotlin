package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.EntertainementViewModel
import com.guiado.akbhar.viewmodel.FoodViewModel

interface EntertainmentEventListener {

    fun onClickAdSearchListItem(countriesViewModel : EntertainementViewModel, position: Int)
    fun launchNews(countriesViewModel : EntertainementViewModel, position: Int)
    fun launchShare(countriesViewModel : EntertainementViewModel, position: Int)


}