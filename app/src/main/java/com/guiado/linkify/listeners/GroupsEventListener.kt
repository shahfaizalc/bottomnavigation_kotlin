package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}