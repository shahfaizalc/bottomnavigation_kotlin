package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.viewmodel.AdSearchModel
import com.faizal.bottomnavigation.viewmodel.DiscussionModel
import com.faizal.bottomnavigation.viewmodel.GroupsModel
import com.faizal.bottomnavigation.viewmodel.MyAdsModel

interface GroupsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GroupsModel, position: Int)


}