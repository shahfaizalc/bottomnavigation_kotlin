package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}