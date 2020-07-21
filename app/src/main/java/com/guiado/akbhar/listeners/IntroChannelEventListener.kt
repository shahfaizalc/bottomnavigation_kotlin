package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.IntroViewModel

interface IntroChannelEventListener {

    fun onClickYearListItem(channelsViewModel : IntroViewModel, position: Int)

}