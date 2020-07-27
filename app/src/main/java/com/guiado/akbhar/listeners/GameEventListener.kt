package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.GameViewModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel

interface GameEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameViewModel, position: Int)
    fun launchNews(countriesViewModel : GameViewModel, position: Int)
    fun launchShare(countriesViewModel : GameViewModel, position: Int)


}