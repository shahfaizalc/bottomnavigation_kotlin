package com.guiado.grads.listeners

import com.guiado.grads.viewmodel.ChallengeModel
import com.guiado.grads.viewmodel.DiscussionModel

interface ChallengeEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ChallengeModel, position: Int)


}