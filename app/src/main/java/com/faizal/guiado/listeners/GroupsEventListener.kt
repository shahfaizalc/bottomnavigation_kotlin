package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}