package com.reelme.app.listeners

import com.reelme.app.viewmodel.MyFollowModel
import com.reelme.app.viewmodel.MyFollowingModel

interface MyFollowingEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyFollowingModel, position: Int)


}