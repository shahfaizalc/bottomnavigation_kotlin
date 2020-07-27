package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel
import com.guiado.akbhar.viewmodel.SportsViewModel

interface SportsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SportsViewModel, position: Int)
    fun launchNews(countriesViewModel : SportsViewModel, position: Int)
    fun launchShare(countriesViewModel : SportsViewModel, position: Int)


}