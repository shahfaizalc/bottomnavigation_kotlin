package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.MyDiscussionModel

interface MyDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyDiscussionModel, position: Int)


}