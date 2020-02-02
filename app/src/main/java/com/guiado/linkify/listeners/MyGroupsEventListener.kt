package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.*

interface MyGroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyGroupsModel, position: Int)

    fun onClickAdSearchListItemDot(countriesViewModel : MyGroupsModel, position: Int)

}