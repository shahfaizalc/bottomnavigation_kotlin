package com.guiado.koodal.listeners
import com.guiado.koodal.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}