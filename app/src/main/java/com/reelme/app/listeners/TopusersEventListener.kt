package com.reelme.app.listeners

import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.TopusersModel

interface TopusersEventListener {

    fun onClickAdSearchListItem(countriesViewModel : TopusersModel, position: Int)


}