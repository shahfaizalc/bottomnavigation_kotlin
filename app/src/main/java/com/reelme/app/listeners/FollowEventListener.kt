package com.reelme.app.listeners

import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel

interface FollowEventListener {

    fun onClickAdSearchListItem(countriesViewModel : FollowModel, position: Int)


}