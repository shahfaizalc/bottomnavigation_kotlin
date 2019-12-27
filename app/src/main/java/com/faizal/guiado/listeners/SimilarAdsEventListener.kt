package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.SimilarDiscussionModel

interface SimilarAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SimilarDiscussionModel, position: Int)


}