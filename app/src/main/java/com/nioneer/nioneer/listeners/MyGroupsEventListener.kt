package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.*

interface MyGroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyGroupsModel, position: Int)

    fun onClickAdSearchListItemDot(countriesViewModel : MyGroupsModel, position: Int)

}