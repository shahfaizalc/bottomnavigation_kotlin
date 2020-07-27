package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.BusinessViewModel
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel

interface BusinessEventListener {

    fun onClickAdSearchListItem(countriesViewModel : BusinessViewModel, position: Int)
    fun launchNews(countriesViewModel : BusinessViewModel, position: Int)
    fun launchShare(countriesViewModel : BusinessViewModel, position: Int)

}