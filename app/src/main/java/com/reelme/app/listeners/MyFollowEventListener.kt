package com.reelme.app.listeners

import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.MyFollowingModel

interface MyFollowEventListener {

    fun onClickAdSearchListItem(countriesViewModel : MyFollowingModel, position: Int)


}