package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.CoronaModel
import com.guiado.akbhar.viewmodel.DiscussionModel

interface CoronaEventListener {

    fun onClickAdSearchListItem(countriesViewModel : CoronaModel, position: Int)
    fun launchNews(countriesViewModel : CoronaModel, position: Int)


}