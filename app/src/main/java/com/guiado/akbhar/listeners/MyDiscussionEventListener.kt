package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.MyDiscussionModel

interface MyDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyDiscussionModel, position: Int)


}