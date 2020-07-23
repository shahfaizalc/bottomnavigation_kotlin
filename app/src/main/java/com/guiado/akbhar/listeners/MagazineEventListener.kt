package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.MagazineViewModel

interface MagazineEventListener {

    fun onClickYearListItem(channelsViewModel : MagazineViewModel, position: Int)

}