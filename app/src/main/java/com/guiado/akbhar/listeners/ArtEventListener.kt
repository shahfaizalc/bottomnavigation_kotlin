package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.ArtViewModel
import com.guiado.akbhar.viewmodel.BusinessViewModel
import com.guiado.akbhar.viewmodel.DiscussionModel

interface ArtEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ArtViewModel, position: Int)
    fun launchNews(countriesViewModel : ArtViewModel, position: Int)
    fun launchShare(countriesViewModel : ArtViewModel, position: Int)


}