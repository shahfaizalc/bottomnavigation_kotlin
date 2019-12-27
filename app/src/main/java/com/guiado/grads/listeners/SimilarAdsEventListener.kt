package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.SimilarDiscussionModel

interface SimilarAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SimilarDiscussionModel, position: Int)


}