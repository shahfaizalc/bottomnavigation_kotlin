package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.*

interface MyGroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyGroupsModel, position: Int)

    fun onClickAdSearchListItemDot(countriesViewModel : MyGroupsModel, position: Int)

}