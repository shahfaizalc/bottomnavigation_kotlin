package com.reelme.app.listeners

import com.reelme.app.viewmodel.MyFollowModel

interface MyFollowEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyFollowModel, position: Int)


}