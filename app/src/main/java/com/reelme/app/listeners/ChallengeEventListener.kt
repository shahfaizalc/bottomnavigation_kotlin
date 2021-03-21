package com.reelme.app.listeners

import com.reelme.app.viewmodel.ChallengeModel

interface ChallengeEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ChallengeModel, position: Int)


}