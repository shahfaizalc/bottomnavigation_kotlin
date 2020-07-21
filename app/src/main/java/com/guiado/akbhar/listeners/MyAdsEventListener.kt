package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}