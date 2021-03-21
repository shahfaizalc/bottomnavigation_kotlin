package com.reelme.app.listeners

import com.reelme.app.viewmodel.GoalModel

interface GoalEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GoalModel, position: Int)


}