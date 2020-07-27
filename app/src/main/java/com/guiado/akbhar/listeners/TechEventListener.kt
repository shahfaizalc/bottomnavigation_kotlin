package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel
import com.guiado.akbhar.viewmodel.TechViewModel

interface TechEventListener {

    fun onClickAdSearchListItem(countriesViewModel : TechViewModel, position: Int)
    fun launchNews(countriesViewModel : TechViewModel, position: Int)
    fun launchShare(countriesViewModel : TechViewModel, position: Int)


}