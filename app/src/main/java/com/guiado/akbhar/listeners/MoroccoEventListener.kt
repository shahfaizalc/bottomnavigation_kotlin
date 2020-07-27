package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel

interface MoroccoEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MoroccoViewModel, position: Int)
    fun launchNews(countriesViewModel : MoroccoViewModel, position: Int)
    fun launchShare(countriesViewModel : MoroccoViewModel, position: Int)


}