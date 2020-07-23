package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.NewsProvidersViewModel

interface IntroChannelEventListener {

    fun onClickYearListItem(channelsViewModel : NewsProvidersViewModel, position: Int)

}