package com.nioneer.nioneer.listeners
import com.nioneer.nioneer.viewmodel.GameChooserModel

interface ChooserEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GameChooserModel, position: Int)


}