package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.viewmodel.AdSearchModel
import com.faizal.bottomnavigation.viewmodel.DiscussionModel
import com.faizal.bottomnavigation.viewmodel.MyAdsModel
import com.faizal.bottomnavigation.viewmodel.MyDiscussionModel

interface MyDiscussionEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyDiscussionModel, position: Int)


}