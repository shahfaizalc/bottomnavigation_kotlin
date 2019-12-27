package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}