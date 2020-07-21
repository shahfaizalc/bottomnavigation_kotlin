package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.*

interface MyGroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyGroupsModel, position: Int)

    fun onClickAdSearchListItemDot(countriesViewModel : MyGroupsModel, position: Int)

}