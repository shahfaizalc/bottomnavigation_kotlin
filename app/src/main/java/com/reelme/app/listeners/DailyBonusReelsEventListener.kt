package com.reelme.app.listeners

import com.reelme.app.viewmodel.DailyBonusReelsModel
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.ReferModel

interface DailyBonusReelsEventListener {

    fun onClickAdSearchListItem(countriesViewModel : DailyBonusReelsModel, position: Int)


}