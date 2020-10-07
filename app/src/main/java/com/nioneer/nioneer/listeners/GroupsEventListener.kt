package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.GroupsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}