package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.viewmodel.AdSearchModel

interface AdSearchEventListener {

    fun onClickAdSearchListItem(countriesViewModel : AdSearchModel, position: Int)


}