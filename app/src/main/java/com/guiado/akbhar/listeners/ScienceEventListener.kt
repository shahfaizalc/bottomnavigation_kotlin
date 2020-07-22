package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.ScienceViewModel

interface ScienceEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ScienceViewModel, position: Int)
    fun launchNews(countriesViewModel : ScienceViewModel, position: Int)


}