package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}