package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel
import com.guiado.akbhar.viewmodel.WorldViewModel

interface WorldEventListener {

    fun onClickAdSearchListItem(countriesViewModel : WorldViewModel, position: Int)
    fun launchNews(countriesViewModel : WorldViewModel, position: Int)
    fun launchShare(countriesViewModel : WorldViewModel, position: Int)


}