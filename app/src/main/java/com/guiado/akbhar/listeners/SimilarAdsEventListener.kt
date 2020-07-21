package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.SimilarDiscussionModel

interface SimilarAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SimilarDiscussionModel, position: Int)


}