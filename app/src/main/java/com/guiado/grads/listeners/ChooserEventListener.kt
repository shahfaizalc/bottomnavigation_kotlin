package com.guiado.grads.listeners
import com.guiado.grads.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}