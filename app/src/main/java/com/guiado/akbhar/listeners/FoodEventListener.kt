package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.FoodViewModel

interface FoodEventListener {

    fun onClickAdSearchListItem(countriesViewModel : FoodViewModel, position: Int)
    fun launchNews(countriesViewModel : FoodViewModel, position: Int)


}