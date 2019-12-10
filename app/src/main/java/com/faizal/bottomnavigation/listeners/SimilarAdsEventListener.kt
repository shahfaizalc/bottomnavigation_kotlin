package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.viewmodel.AdSearchModel
import com.faizal.bottomnavigation.viewmodel.MyAdsModel
import com.faizal.bottomnavigation.viewmodel.SimilarDiscussionModel

interface SimilarAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SimilarDiscussionModel, position: Int)


}