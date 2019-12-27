package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}