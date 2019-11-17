package com.faizal.bottomnavigation.listeners
import com.faizal.bottomnavigation.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}