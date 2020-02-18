package com.guiado.koodal.listeners

import com.guiado.koodal.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}