package com.reelme.realme.listeners

import com.reelme.realme.viewmodel.GoalModel

interface GoalEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GoalModel, position: Int)


}