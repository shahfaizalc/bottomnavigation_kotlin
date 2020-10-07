package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.MyAdsModel

interface MyAdsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyAdsModel, position: Int)


}