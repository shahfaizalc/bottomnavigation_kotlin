package com.faizal.guiado.listeners

import com.faizal.guiado.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}