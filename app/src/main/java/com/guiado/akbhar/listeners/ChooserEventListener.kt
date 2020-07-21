package com.guiado.akbhar.listeners
import com.guiado.akbhar.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}