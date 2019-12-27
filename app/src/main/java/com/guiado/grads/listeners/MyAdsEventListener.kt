package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}