package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.ChallengeModel
import com.guiado.grads.viewmodel.DiscussionModel
import com.guiado.grads.viewmodel.GoalModel

interface GoalEventListener {

    fun onClickAdSearchListItem(countriesViewModel : GoalModel, position: Int)


}