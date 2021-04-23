package com.reelme.app.listeners

import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.ReferModel

interface ReferEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ReferModel, position: Int)


}