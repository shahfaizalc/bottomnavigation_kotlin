package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}