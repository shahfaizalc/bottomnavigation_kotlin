package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}