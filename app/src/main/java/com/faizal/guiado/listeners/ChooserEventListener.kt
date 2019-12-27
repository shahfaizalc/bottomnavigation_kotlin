package com.faizal.guiado.listeners
import com.faizal.guiado.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}