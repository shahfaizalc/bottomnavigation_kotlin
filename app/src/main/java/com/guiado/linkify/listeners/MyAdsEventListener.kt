package com.guiado.linkify.listeners

import com.guiado.linkify.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}