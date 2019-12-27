package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.MyDiscussionModel

interface MyDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyDiscussionModel, position: Int)


}