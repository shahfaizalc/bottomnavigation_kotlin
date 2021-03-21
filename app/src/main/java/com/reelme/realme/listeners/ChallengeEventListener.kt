package com.reelme.realme.listeners

import com.reelme.realme.viewmodel.ChallengeModel

interface ChallengeEventListener {

    fun onClickAdSearchListItem(countriesViewModel : ChallengeModel, position: Int)


}