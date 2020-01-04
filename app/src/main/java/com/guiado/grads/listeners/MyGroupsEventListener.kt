package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.*

interface MyGroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyGroupsModel, position: Int)

    fun onClickAdSearchListItemDot(countriesViewModel : MyGroupsModel, position: Int)

}