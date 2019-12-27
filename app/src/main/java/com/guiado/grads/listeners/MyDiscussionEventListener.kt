package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.MyDiscussionModel

interface MyDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyDiscussionModel, position: Int)


}