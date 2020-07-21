package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}