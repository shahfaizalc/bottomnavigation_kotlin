package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}