package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.SimilarDiscussionModel

interface SimilarAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : SimilarDiscussionModel, position: Int)


}